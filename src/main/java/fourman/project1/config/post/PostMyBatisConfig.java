package fourman.project1.config.post;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("fourman.project1.repository.post")
public class PostMyBatisConfig {
}
