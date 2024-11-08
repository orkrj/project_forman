package fourman.project1.config.traffic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("fourman.project1.repository.traffic")
public class TrafficMyBatisConfig {
}
