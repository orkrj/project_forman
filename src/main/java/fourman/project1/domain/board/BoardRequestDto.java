package fourman.project1.domain.board;

import fourman.project1.domain.post.Post;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@Builder
public class BoardRequestDto {
    //private Long boardId;
    private String boardname;
    private List<Post> posts;
}
