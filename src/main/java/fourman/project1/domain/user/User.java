package fourman.project1.domain.user;

import fourman.project1.domain.post.Post;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long userId;

    private String username;

    private String password;

    private List<Post> posts;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private ZonedDateTime deletedAt;


    @Builder
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

}
