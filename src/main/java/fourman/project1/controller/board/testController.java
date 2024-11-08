package fourman.project1.controller.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {
    @GetMapping("/aaa")
    public String test() {
        return "create-traffic";
    }

    @GetMapping("/bbb")
    public String test1() {
        return "detailed-traffic";
    }
}
