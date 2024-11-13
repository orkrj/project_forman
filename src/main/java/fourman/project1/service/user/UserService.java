package fourman.project1.service.user;

import fourman.project1.domain.user.User;
import fourman.project1.domain.user.UserDetailResponseDto;
import fourman.project1.exception.user.UserNotFoundException;
import fourman.project1.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

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

     @Transactional
     public boolean isUsernameAvailable(String username) {
        return userRepository.existsByUsername(username);
     }

        @Transactional(readOnly = true)
        public UserDetailResponseDto getUserDetail(Long userId) {
            User user = userRepository.findByUserId(userId);

            if (user == null) {
                throw new UserNotFoundException("사용자를 찾을 수 없습니다.: " + userId);
            }
            String formattedDate = user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            return UserDetailResponseDto.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .createdAt(formattedDate)
                    .build();
        }
}
