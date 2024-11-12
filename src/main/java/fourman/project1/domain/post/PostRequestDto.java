package fourman.project1.domain.post;

import fourman.project1.domain.board.Board;
import fourman.project1.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    @NotBlank(message = "제목이 비어있을 수 없습니다.")
    @Size(min = 1, max = 30, message = "제목은 1글자 이상 30글자 미만이어야 합니다.")
    private String title;

    @NotBlank(message = "내용이 비어있을 수 없습니다.")
    private String body;

    private Long userId;

    private Long boardId;

}
