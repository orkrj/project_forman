package fourman.project1.domain.user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// 도메인으로 이동
@Getter
@Setter
public class UserSignUpRequestDto {
    private String username;
    private String password;

    public UserSignUpRequestDto(){};

}
