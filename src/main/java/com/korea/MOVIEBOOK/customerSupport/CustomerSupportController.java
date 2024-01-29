package com.korea.MOVIEBOOK.customerSupport;

import com.korea.MOVIEBOOK.customerSupport.question.Question;
import com.korea.MOVIEBOOK.customerSupport.question.QuestionService;
import com.korea.MOVIEBOOK.member.Member;
import com.korea.MOVIEBOOK.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customerSupport")
public class CustomerSupportController {

    private final QuestionService questionService;
    private final MemberService memberService;

    @GetMapping("notice")
    public String mainPage() {
        return "customerSupport/customerSupportNotice";
    }

    @GetMapping("myQuestion")
    public String myQuestion(Principal principal, Model model) {
        Member member = memberService.getMember(principal.getName());
        List<Question> myQuestion = questionService.getMyQuestionList(member);
        model.addAttribute("myQuestion", myQuestion);
        return "customerSupport/customerSupportMyQuestion";
    }

    @GetMapping("question")
    public String question(Model model) {
        List<Question> questionList = questionService.getQuestionList();
        model.addAttribute("questionList", questionList);
        return "customerSupport/customerSupportQuestion";
    }

    @GetMapping("FAQ")
    public String FAQ() {
        return "customerSupport/customerSupportFAQ";
    }
}
