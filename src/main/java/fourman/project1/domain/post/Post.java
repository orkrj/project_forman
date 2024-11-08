package fourman.project1.domain.post;

import fourman.project1.domain.traffic.Traffic;
import fourman.project1.domain.user.User;
import fourman.project1.domain.board.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private Long postId;

    private String title;

    private String body;

    private User user;

    private Board board;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private ZonedDateTime deletedAt;

    public static Post from(PostRequestDto postRequestDto) {
        Post post = new Post();
        post.title = postRequestDto.getTitle();
        post.body = postRequestDto.getBody();

        return post;
    }
}
