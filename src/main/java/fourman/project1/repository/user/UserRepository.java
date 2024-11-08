    package fourman.project1.repository.user;

    import fourman.project1.domain.user.User;

    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Repository;
    import org.springframework.transaction.annotation.Transactional;


    @Repository
    @RequiredArgsConstructor
    public class UserRepository {

        private final UserMyBatisMapper userMybatisMapper;

        @Transactional
        public void save(User user) {
            userMybatisMapper.save(user);
        }


        public User findByUsername(String username) {
            return userMybatisMapper.findByUsername(username);
        }


        public boolean existsByUsername(String username) {
            return !userMybatisMapper.existsByUsername(username);
        }
    }
