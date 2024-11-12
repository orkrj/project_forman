package fourman.project1.service.user;

import fourman.project1.domain.user.User;
import fourman.project1.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class UserService  {

        private final BCryptPasswordEncoder bCryptPasswordEncoder;
        private final UserRepository userRepository;

        @Transactional
        public void signUp(User user) {

            User newUser = User.builder()
                    .username(user.getUsername())
                    .password(bCryptPasswordEncoder.encode(user.getPassword()))
                    .build();

            userRepository.save(newUser);
        }

    public boolean isUsernameAvailable(String username) {
        return userRepository.existsByUsername(username);
    }

}
