package fourman.project1.config.board;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("fourman.project1.repository.board")
public class BoardMyBatsConfig {
}
