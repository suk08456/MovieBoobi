package com.korea.MOVIEBOOK.heart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/heart")
public class HeartController {
    private final HeartService heartService;

    @PostMapping("/{category}/{contentsID}/{heartClick}")
    public String reviewList(@PathVariable("category") String category,@PathVariable("contentsID") String contentsID,@PathVariable("heartClick") String heartClick,@RequestParam(name = "url")String url, Model model, Principal principal) {
        if (Objects.equals(heartClick, "true")) {
            this.heartService.plusContentsHeart(principal, category, contentsID);
        } else {
            this.heartService.minusContentsHeart(principal, category, contentsID);
        }
        return "redirect:" + url;
    }
}
