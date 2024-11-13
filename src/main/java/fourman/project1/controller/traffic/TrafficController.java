package fourman.project1.controller.traffic;

import fourman.project1.domain.traffic.Traffic;
import fourman.project1.domain.traffic.TrafficMapper;
import fourman.project1.domain.traffic.TrafficRequestDto;
import fourman.project1.domain.traffic.TrafficResponseDto;
import fourman.project1.service.traffic.TrafficService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String createTraffic(@ModelAttribute TrafficRequestDto trafficRequestDto, Model model) {
        CompletableFuture<Long> trafficId = trafficService.createTraffic(Traffic.from(trafficRequestDto));
        return "redirect:/traffics/vus/" + trafficId.join();
    }

    @GetMapping
    public String findTraffics(Model model) {
        List<TrafficResponseDto> traffics = trafficService.findTraffics().stream()
                                                .map(trafficMapper::trafficToTrafficResponseDto)
                                                .toList();
        model.addAttribute("traffics", traffics);
        return "traffics";
    }

    //== 권한이 필요 없는 조회 ==//
    @GetMapping("/vus/{trafficId}")
    public String findTrafficByIdPublic(@PathVariable Long trafficId, Model model) {
        model.addAttribute(
                "traffic",
                trafficMapper.trafficToTrafficResponseDto(trafficService.findTrafficById(trafficId))
        );

        return "detailed-traffic";
    }

    //== 권한이 필요한 조회 ==//
    @GetMapping("/{trafficId}")
    public String findTrafficByIdPrivate(@PathVariable Long trafficId, Model model) {
        model.addAttribute(
                "traffic",
                trafficMapper.trafficToTrafficResponseDto(trafficService.findTrafficById(trafficId))
        );

        return "edit-traffic";
    }

    @PatchMapping("/{trafficId}")
    public String updateTraffic(
            @PathVariable Long trafficId, @ModelAttribute TrafficRequestDto trafficRequestDto
    ) {
        trafficService.updateTraffic(trafficId, trafficRequestDto);
        return "redirect:/traffics/vus/" + trafficId;
    }

    @PostMapping("/{trafficId}")
    public String deleteTraffic(@PathVariable Long trafficId) {
        trafficService.deleteTraffic(trafficId);
        return "redirect:/traffics";
    }
}
