package fourman.project1.controller.traffic;

import fourman.project1.domain.traffic.Traffic;
import fourman.project1.domain.traffic.TrafficMapper;
import fourman.project1.domain.traffic.TrafficRequestDto;
import fourman.project1.domain.traffic.TrafficResponseDto;
import fourman.project1.domain.user.CustomUserDetails;
import fourman.project1.service.traffic.TrafficService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Controller
@RequestMapping("/traffics")
@RequiredArgsConstructor
public class TrafficController {

    private final TrafficMapper trafficMapper;
    private final TrafficService trafficService;

    @GetMapping("/create")
    public String createTraffic() {
        return "create-traffic";
    }

    @PostMapping("/create")
    public String createTraffic(
            @Validated @ModelAttribute TrafficRequestDto trafficRequestDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        if (bindingResult.hasErrors()) {
            return "create-traffic";
        }

        CompletableFuture<Long> trafficId =
                trafficService.createTraffic(Traffic.from(trafficRequestDto), user.getUser());
        return "redirect:/traffics/vus/" + trafficId.join();
    }

    @GetMapping
    public String findTraffics(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        List<TrafficResponseDto> traffics = trafficService.findTraffics(user.getUser()).stream()
                                                .map(trafficMapper::trafficToTrafficResponseDto)
                                                .toList();
        model.addAttribute("traffics", traffics);
        return "traffics";
    }

    //== 권한이 필요 없는 조회 ==//
    @GetMapping("/vus/{trafficId}")
    public String findTrafficByIdPublic(@PathVariable Long trafficId, Model model) {
        model.addAttribute("traffic", trafficService.findTrafficByIdPublic(trafficId));
        return "detailed-traffic";
    }

    //== 권한이 필요한 조회 ==//
    @GetMapping("/{trafficId}")
    public String findTrafficByIdPrivate(
            @PathVariable Long trafficId,
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        model.addAttribute(
                "traffic",
                trafficMapper.trafficToTrafficResponseDto(
                        trafficService.findTrafficById(trafficId, user.getUser())
                )
        );

        return "edit-traffic";
    }

    @PatchMapping("/{trafficId}")
    public String updateTraffic(
            @PathVariable Long trafficId,
            @Validated @ModelAttribute TrafficRequestDto trafficRequestDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        if (bindingResult.hasErrors()) {
            return "edit-traffic";
        }

        trafficService.updateTraffic(trafficId, trafficRequestDto, user.getUser());
        return "redirect:/traffics/vus/" + trafficId;
    }

    @PostMapping("/{trafficId}")
    public String deleteTraffic(
            @PathVariable Long trafficId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        trafficService.deleteTraffic(trafficId, user.getUser());
        return "redirect:/traffics";
    }
}
