package fourman.project1.domain.user;

import lombok.Data;

// 도메인으로 이동
@Data
public class UserSignUpRequestDto {
    private String username;
    private String password;
}
