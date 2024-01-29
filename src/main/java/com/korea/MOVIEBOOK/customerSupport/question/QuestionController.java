package com.korea.MOVIEBOOK.customerSupport.question;

import com.korea.MOVIEBOOK.member.Member;
import com.korea.MOVIEBOOK.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
    @RequestMapping("customerSupport/question")
public class QuestionController {

    private final QuestionService questionService;
    private final MemberService memberService;

    @GetMapping("/questionForm")
    public String questionForm() {
        return "/customerSupport/question/questionForm";
    }

    @PostMapping("/createQuestion")
    public String createQuestion(Principal principal, @RequestParam String title, @RequestParam String content) {
        Member member = memberService.getMember(principal.getName());
        questionService.create(member, title, content);
        return "redirect:/customerSupport/question";
    }

    @GetMapping("/detail")
    public String questionDetail(Long questionId, Model model) {
        Question question = questionService.findByQuestionId(questionId);
        if (question != null) {
            model.addAttribute("question", question);
        }
        return "/customerSupport/question/questionDetail";
    }
}
