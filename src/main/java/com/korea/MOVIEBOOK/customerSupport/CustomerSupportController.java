package com.korea.MOVIEBOOK.customerSupport;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customerSupport")
public class CustomerSupportController {
    @GetMapping("")
    public String mainPage() {
        return "customerSupport/customerSupportMainPage";
    }
}
