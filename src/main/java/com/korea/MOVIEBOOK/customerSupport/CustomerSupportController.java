package com.korea.MOVIEBOOK.customerSupport;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customerSupport")
public class CustomerSupportController {
    @GetMapping("notice")
    public String mainPage() {
        return "customerSupport/customerSupportNotice";
    }

    @GetMapping("oneOnOne")
    public String oneOnOne() {
        return "customerSupport/customerSupportOneOnOne";
    }

    @GetMapping("myQuestion")
    public String myQuestion() {
        return "customerSupport/customerSupportMyQuestion";
    }

    @GetMapping("FAQ")
    public String FAQ() {
        return "customerSupport/customerSupportFAQ";
    }
}
