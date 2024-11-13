package fourman.project1.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequestDto {
    @NotBlank(message = "username은 비어 있을 수 없습니다.")
    @Size(min = 4, max = 10, message = "username은 4자 이상 10자 이하로 입력해야 합니다.")
    private String username;

    @NotBlank(message = "password는 비어 있을 수 없습니다.")
    @Size(min = 6, max = 12, message = "password는 6자 이상 12자 이하로 입력해야 합니다.")
    private String password;

    public UserSignUpRequestDto(){};

}
