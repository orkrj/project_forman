package fourman.project1.domain.board;

import fourman.project1.domain.post.Post;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BoardResponseDto {
    private Long boardId;
    private List<Post> posts;
}
