package fourman.project1.domain.post;

import fourman.project1.domain.board.Board;
import fourman.project1.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
//    private Long postId;

    private String title;

    private String body;

    private Long userId;

    private Long boardId;

//    private ZonedDateTime createdAt;
//
//    private ZonedDateTime updatedAt;
//
//    private ZonedDateTime deletedAt;
}
