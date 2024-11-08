package fourman.project1.repository.user;

import fourman.project1.domain.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMyBatisMapper {

    void save(User user);

    boolean existsByUsername(String username);
    User findByUsername(String username);

}
