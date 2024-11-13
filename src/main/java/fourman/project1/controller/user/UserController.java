package fourman.project1.controller.user;
import fourman.project1.domain.user.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import fourman.project1.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("userSignUpRequestDto", new UserSignUpRequestDto());

        return "/user/join";
    }

    @GetMapping("/user/detail")
    public String detail(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            if (customUserDetails.getUserId() == null) {
                throw new IllegalArgumentException("유효하지 않은 접근입니다.");
            }
            model.addAttribute("detailUser", userService.getUserDetail(customUserDetails.getUserId()));

        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error/invalidUser";
        }
        return "/user/detail";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userSignUpRequestDto", new UserSignUpRequestDto());
        return "/user/login";
    }

    @PostMapping("/join")
    public String signUp(@ModelAttribute UserSignUpRequestDto userSignUpRequestDto) {
        User user = User.builder()
                .username(userSignUpRequestDto.getUsername())
                .password(userSignUpRequestDto.getPassword())
                .build();

        userService.signUp(user);
        return "redirect:/login";
    }

    @PostMapping("/check-username")
    @ResponseBody
    public boolean checkUsername(@RequestBody CheckUsernameRequestDto checkUsernameRequestDto) {
        return userService.isUsernameAvailable(checkUsernameRequestDto.getUsername());
    }

}