package com.korea.MOVIEBOOK.customerSupport.question;

import com.korea.MOVIEBOOK.customerSupport.Category;
import com.korea.MOVIEBOOK.member.Member;
import com.korea.MOVIEBOOK.member.MemberCreateForm;
import com.korea.MOVIEBOOK.member.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String questionForm(Model model, QuestionCreateForm questionCreateForm) {
        model.addAttribute("category", Category.QUESTION);
        return "/customerSupport/question/questionForm";
    }

    @PostMapping("/createQuestion")
    public String createQuestion(Principal principal, @Valid @NotNull QuestionCreateForm questionCreateForm, BindingResult bindingResult) {
        Member member = memberService.getMember(principal.getName());
        if (bindingResult.hasErrors()) {
            return "/customerSupport/question/questionForm";
        }
        questionService.create(member, questionCreateForm.getTitle(), questionCreateForm.getContent(), Category.QUESTION);
        return "redirect:/customerSupport/question";
    }

    @GetMapping("/noticeForm")
    public String noticeForm(Model model) {
        model.addAttribute("category", Category.NOTICE);
        return "/customerSupport/question/questionForm";
    }

    @PostMapping("/createNotice")
    public String createNotice(Principal principal, @RequestParam String title, @RequestParam String content) {
        Member member = memberService.getMember(principal.getName());
        questionService.create(member, title, content, Category.NOTICE);
        return "redirect:/customerSupport/notice";
    }

    @GetMapping("/FAQForm")
    public String FAQForm(Model model) {
        model.addAttribute("category", Category.FAQ);
        return "/customerSupport/question/questionForm";
    }

    @PostMapping("/createFAQ")
    public String createFAQ(Principal principal, @RequestParam String title, @RequestParam String content) {
        Member member = memberService.getMember(principal.getName());
        questionService.create(member, title, content, Category.FAQ);
        return "redirect:/customerSupport/FAQ";
    }

    @GetMapping("/detail")
    public String questionDetail(Long questionId, Model model) {
        Question question = questionService.findByQuestionId(questionId);
        if (question != null) {
            model.addAttribute("question", question);
            model.addAttribute("category", question.getCategory());
        }
        return "/customerSupport/question/questionDetail";
    }
}
