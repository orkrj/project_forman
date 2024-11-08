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
public class PostResponseDto {
    private Long postId;

    private String title;

    private String body;

    private User user;  //나중에 responseDto

    private Board board;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private ZonedDateTime deletedAt;
}
