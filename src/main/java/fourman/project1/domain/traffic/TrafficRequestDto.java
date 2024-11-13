package fourman.project1.domain.traffic;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class TrafficRequestDto {

    private String url;

    @NotNull
    @Min(1)
    @Max(10000)
    private int vus;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 3)
    private String duration;
}
