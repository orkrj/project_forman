package fourman.project1.domain.user;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Builder
@Getter
public class UserDetailResponseDto {
    private String username;
    private String password;
    private String createdAt;
}
