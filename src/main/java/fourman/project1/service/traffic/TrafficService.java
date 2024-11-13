package fourman.project1.service.traffic;

import fourman.project1.domain.traffic.Traffic;
import fourman.project1.domain.traffic.TrafficRequestDto;
import fourman.project1.domain.traffic.TrafficResponseDto;
import fourman.project1.domain.user.User;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface TrafficService {

    List<Traffic> findTraffics(User user);

    Traffic findTrafficById(Long trafficId, User user);

    TrafficResponseDto findTrafficByIdPublic(Long trafficId);

    CompletableFuture<Long> createTraffic(Traffic traffic, User user);

    void updateTraffic(Long trafficId, TrafficRequestDto trafficRequestDto, User user);

    void deleteTraffic(Long trafficId, User user);
}
