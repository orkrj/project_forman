package fourman.project1.config;

import fourman.project1.domain.board.Board;
import fourman.project1.domain.post.Post;
import fourman.project1.domain.user.User;
import fourman.project1.repository.board.BoardMyBatisMapper;
import fourman.project1.repository.post.PostMyBatisMapper;
import fourman.project1.repository.user.UserMyBatisMapper;
import fourman.project1.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DummyDataConfig {

    private final UserMyBatisMapper userMyBatisMapper;
    private final PostMyBatisMapper postMyBatisMapper;

    @Bean
    public CommandLineRunner createDummyData(UserRepository userRepository) {
        return args -> {
            Faker faker = new Faker();

            for (int i = 0; i < 10; i++) {
                User user = new User(faker.name().firstName(), faker.internet().password(6, 12));
                userMyBatisMapper.save(user);
            }

            Board board = new Board();
            board.setBoardId((long) 1);

            for (int i = 0; i < 300; i++) {
                Post post = new Post();
                post.setTitle(faker.brand().watch());
                post.setBody(faker.lorem().paragraph());
                post.setBoard(board);
                postMyBatisMapper.createPost(post);
            }
        };
    }
}
