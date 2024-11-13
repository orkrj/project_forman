package fourman.project1.service.traffic;

import fourman.project1.domain.traffic.Traffic;
import fourman.project1.domain.traffic.TrafficRequestDto;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface TrafficService {

    List<Traffic> findTraffics();

    Traffic findTrafficById(Long trafficId);

    CompletableFuture<Long> createTraffic(Traffic traffic);

    void updateTraffic(Long trafficId, TrafficRequestDto trafficRequestDto);

    void deleteTraffic(Long trafficId);
}
