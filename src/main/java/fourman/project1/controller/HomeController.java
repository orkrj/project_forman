package fourman.project1.controller;

import fourman.project1.domain.user.UserSignUpRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("userSignUpRequestDto", new UserSignUpRequestDto());
        return "user/login";
    }
}
