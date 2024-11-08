package fourman.project1.config.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("fourman.project1.repository.user")
public class UserMyBatisConfig {
}
