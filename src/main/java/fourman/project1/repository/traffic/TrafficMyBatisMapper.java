package fourman.project1.repository.traffic;

import fourman.project1.domain.traffic.Traffic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TrafficMyBatisMapper {

    List<Traffic> findTraffics();

    Optional<Traffic> findTrafficById(Long trafficId);

    void createTraffic(Traffic traffic);

    void updateTraffic(Traffic findTraffic);

    void setTrafficUrl(@Param("url") String trafficUrl, @Param("id") Long trafficId);

    void setReqs(@Param("totalReq") Long totalReq, @Param("avgReq") Long avgReq, @Param("id") Long trafficId);

    void deleteTraffic(Long trafficId);

    void forceDeleteTrafficDueToError(Long trafficId);
}
