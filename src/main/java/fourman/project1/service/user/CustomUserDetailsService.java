package fourman.project1.service.user;

import fourman.project1.domain.user.User;
import fourman.project1.domain.user.CustomUserDetails;
import fourman.project1.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        User user =userRepository.findByUsername(username);
        System.out.println("Stored password for username " + username + ": " + user.getPassword());
        if(user != null) {
            return new CustomUserDetails(user);
        }

        throw new UsernameNotFoundException("유저를 찾을 수 없습니다. " + username);
    }
}
