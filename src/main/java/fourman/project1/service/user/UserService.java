package fourman.project1.service.user;

import fourman.project1.domain.user.User;
import fourman.project1.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class UserService  {

        private final BCryptPasswordEncoder bCryptPasswordEncoder;
        private final UserRepository userRepository;


        public void signUp(User user) {


            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setCreatedAt(ZonedDateTime.now());
            userRepository.save(user);
        }

    public boolean isUsernameAvailable(String username) {
        return userRepository.existsByUsername(username);
    }

}
