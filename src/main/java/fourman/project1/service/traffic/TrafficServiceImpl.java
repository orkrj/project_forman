package fourman.project1.service.traffic;

import fourman.project1.domain.traffic.Traffic;
import fourman.project1.domain.traffic.TrafficMapper;
import fourman.project1.domain.traffic.TrafficRequestDto;
import fourman.project1.domain.traffic.TrafficResponseDto;
import fourman.project1.domain.user.CustomUserDetails;
import fourman.project1.domain.user.User;
import fourman.project1.exception.traffic.*;
import fourman.project1.repository.traffic.TrafficMyBatisMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrafficServiceImpl implements TrafficService {

    private final TrafficMapper trafficMapper;
    private final TrafficMyBatisMapper trafficMyBatisMapper;

    @Value("${path.url.local}")
    private String localUrl;

    @Value("${path.script}")
    private String scriptPath;

    @Value("${path.k6}")
    private String k6Path;

    @Override
    public List<Traffic> findTraffics(User user) {
        return trafficMyBatisMapper.findTraffics(user.getUserId());
    }

    @Override
    public Traffic findTrafficById(Long trafficId, User user) {
        Traffic findTraffic = trafficMyBatisMapper.findTrafficById(trafficId)
                .orElseThrow(TrafficNotFoundException::new);

        if (findTraffic.getUserId().equals(user.getUserId())) {
            return findTraffic;
        } else {
            throw new TrafficAccessDeniedException();
        }
    }

    @Override
    @Cacheable(cacheNames = "vusGetRequest", key = "#trafficId", cacheManager = "cacheManager")
    public TrafficResponseDto findTrafficByIdPublic(Long trafficId) {
        return trafficMapper.trafficToTrafficResponseDto(
                trafficMyBatisMapper.findTrafficById(trafficId)
                        .orElseThrow(TrafficNotFoundException::new)
        );
    }

    @Async
    @Override
    public CompletableFuture<Long> createTraffic(Traffic traffic, User user) {
        traffic.setUserInfo(user, user.getUserId());
        traffic.setDuration(traffic.getDuration() + "s");
        trafficMyBatisMapper.createTraffic(traffic);

        Long trafficId = traffic.getTrafficId();
        String trafficUrl = localUrl + "/traffics/vus/" + trafficId;

        traffic.setUrl(trafficUrl);

        executeK6(traffic, trafficUrl, trafficId);

        return CompletableFuture.completedFuture(trafficId);
    }

    @Async
    @Override
    public void updateTraffic(Long trafficId, TrafficRequestDto trafficRequestDto, User user) {
        Traffic findTraffic = findTrafficById(trafficId, user);

        findTraffic.setVus(trafficRequestDto.getVus());
        findTraffic.setDuration(trafficRequestDto.getDuration() + "s");
        findTraffic.setUpdatedAt(ZonedDateTime.now());
        trafficMyBatisMapper.updateTraffic(findTraffic);

        executeK6(findTraffic, findTraffic.getUrl(), trafficId);
    }

    @Override
    public void deleteTraffic(Long trafficId, User user) {
        Traffic findTraffic = findTrafficById(trafficId, user);
        if (findTraffic.getUserId().equals(user.getUserId())) {
            trafficMyBatisMapper.deleteTraffic(trafficId);
        } else {
            throw new TrafficAccessDeniedException();
        }
    }

    private void executeK6(Traffic traffic, String trafficUrl, Long trafficId) {
        CompletableFuture.runAsync(() -> {
            trafficMyBatisMapper.setTrafficUrl(trafficUrl, trafficId);

            try {
                ProcessBuilder processBuilder = new ProcessBuilder(
                        k6Path, "run",
                        "--vus", String.valueOf(traffic.getVus()),
                        "--duration", traffic.getDuration(),
                        "--env", "TARGET_URL=" + traffic.getUrl(),
                        scriptPath
                );
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line).append(System.lineSeparator());
                    }

                    parseHttpReqs(result.toString(), traffic);
                }

                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    throw new TrafficK6CmdErrorException("execution failed with exit code: " + exitCode);
                }

            } catch (IOException | InterruptedException e) {
                trafficMyBatisMapper.forceDeleteTrafficDueToError(traffic.getTrafficId());
                throw new TrafficK6CmdErrorException(e);
            }
        });
    }

    private void parseHttpReqs(String result, Traffic traffic) {
        String[] lines = result.split(System.lineSeparator());
        boolean isFindHttpReqs = false;

        for (String line : lines) {
            if (line.contains("http_reqs")) {
                String[] httpReqs = line.split(":")[1].trim().split("\\s+");
                Long totalReq = Long.parseLong(httpReqs[0]);
                Long averageReqPerSecond = Long.parseLong(httpReqs[1].split("\\.")[0]);
                isFindHttpReqs = true;

                saveReqs(traffic, totalReq, averageReqPerSecond);
            }
        }

        if (!isFindHttpReqs) {
            throw new TrafficNotFoundHttpReqsException();
        }
    }

    private void saveReqs(Traffic traffic, Long totalReq, Long averageReqPerSecond) {
        traffic.setReqs(totalReq, averageReqPerSecond);
        trafficMyBatisMapper.setReqs(totalReq, averageReqPerSecond, traffic.getTrafficId());
    }

    // SecurityContextHolder 을 몾 잡는 이유가 뭐지
//    private User findUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails user) {
//            return user.getUser();
//        }
//
//        throw new TrafficUserNotFoundException();
//    }
}
