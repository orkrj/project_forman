package fourman.project1.domain.traffic;

import fourman.project1.domain.board.Board;
import fourman.project1.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter @Setter
@NoArgsConstructor
public class Traffic {

    private Long trafficId;

    private String url;

    private int vus;

    private String duration;

    private Long totalReq;

    private Long avgReq;

    private User user;

    private Board board;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private ZonedDateTime deletedAt;

    public static Traffic from(TrafficRequestDto testRequestDto) {
        Traffic traffic = new Traffic();
        traffic.setUrl(testRequestDto.getUrl());
        traffic.setVus(testRequestDto.getVus());
        traffic.setDuration(testRequestDto.getDuration());
        traffic.setCreatedAt(ZonedDateTime.now());
        traffic.setUpdatedAt(traffic.getCreatedAt());

        return traffic;
    }

    public void setReqs(Long totalReq, Long avgReq) {
        this.totalReq = totalReq;
        this.avgReq = avgReq;
    }
}
