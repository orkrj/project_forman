package fourman.project1.service.traffic;

import fourman.project1.domain.traffic.Traffic;
import fourman.project1.exception.traffic.TrafficK6CmdErrorException;
import fourman.project1.exception.traffic.TrafficNotFoundException;
import fourman.project1.repository.traffic.TrafficMyBatisMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrafficServiceImpl implements TrafficService {

    private final TrafficMyBatisMapper trafficMyBatisMapper;

    @Override
    public List<Traffic> findTraffics() {
        return trafficMyBatisMapper.findTraffics();
    }

    @Override
    public Traffic findTrafficById(Long trafficId) {
        return trafficMyBatisMapper.findTrafficById(trafficId)
                    .orElseThrow(TrafficNotFoundException::new);
    }

    @Async
    @Override
    public void createTraffic(Traffic traffic) {
        traffic.setDuration(traffic.getDuration() + "s");
        trafficMyBatisMapper.createTraffic(traffic);

        String LOCAL_HOST_URL = "http://localhost:8080"; // @Value
        Long trafficId = traffic.getTrafficId();
        String trafficUrl = LOCAL_HOST_URL + "/traffics/" + trafficId;

        traffic.setUrl(trafficUrl);
        trafficMyBatisMapper.setTrafficUrl(trafficUrl, trafficId);

        log.info("traffic url: {}", trafficUrl);
        String SCRIPT_PATH = "/Users/zun/Lecture/Elice/Cloud/project1/k6/script.js";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "/opt/homebrew/bin/k6", "run",
                    "--vus", String.valueOf(traffic.getVus()),
                    "--duration", traffic.getDuration(),
                    "--env", "TARGET_URL=" + traffic.getUrl(),
                    SCRIPT_PATH
            );
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    log.info(line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new TrafficK6CmdErrorException("execution failed with exit code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            trafficMyBatisMapper.forceDeleteTrafficDueToError(traffic.getTrafficId());
            throw new TrafficK6CmdErrorException(e);
        }
    }
}
