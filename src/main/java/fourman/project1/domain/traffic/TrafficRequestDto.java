package fourman.project1.domain.traffic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class TrafficRequestDto {

    private String url;

    private int vus;

    private String duration;
}
