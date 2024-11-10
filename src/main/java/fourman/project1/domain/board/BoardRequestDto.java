package fourman.project1.domain.board;

import fourman.project1.domain.post.Post;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardRequestDto {
    private List<Post> posts;
}
