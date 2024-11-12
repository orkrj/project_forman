package fourman.project1.domain.user;

import fourman.project1.domain.post.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long userId;

    @NotBlank(message = "username은 비어 있을 수 없습니다.")
    @Size(min = 4, max = 10, message = "username은 4자 이상 10자 이하로 입력해야 합니다.")
    private String username;

    @NotBlank(message = "password는 비어 있을 수 없습니다.")
    @Size(min = 6, max = 12, message = "password는 6자 이상 12자 이하로 입력해야 합니다.")
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
