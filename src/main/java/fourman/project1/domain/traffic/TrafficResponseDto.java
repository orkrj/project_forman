package fourman.project1.domain.traffic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class TrafficResponseDto {

    private Long trafficId;

    private int vus;

    private String duration;

    private Long totalReq;

    private Long avgReq;
}
