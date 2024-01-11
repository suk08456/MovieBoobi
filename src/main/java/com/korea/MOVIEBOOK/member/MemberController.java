package com.korea.MOVIEBOOK.member;

import com.korea.MOVIEBOOK.review.ReviewService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;
    private final ReviewService reviewService;

    @GetMapping("/signup")
    public String signup(MemberCreateForm memberCreateForm) {
        return "member/signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {
        if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "비밀번호가 일치하지 않습니다.");
            // password1, 2 일치하는지 확인
            return "member/signup_form";
        }

        try {
            Member member = memberService.create(memberCreateForm.getUsername(),
                    memberCreateForm.getPassword1(), memberCreateForm.getNickname(), memberCreateForm.getEmail());
            // memberService의 create 메서드를 호출하여 memberCreateForm에서 받은 데이터를 기반으로 새 회원을 생성하고
            // 그 결과로 반환된 회원 객체를 member 변수에 할당
            emailService.sendEmail(member.getEmail(), "http://localhost:8888/member/verify?userId=" + member.getId());
            // 이메일 인증 링크 전송
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자");
            return "member/signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "member/signup_form";
        }
        return "redirect:/member/emailVerification";
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("userId") long userId) {
        memberService.verifyEmail(userId);  // userId를 사용하여 이메일 인증 처리
        return "redirect:/member/emailVerificationSuccess";
    }
    @GetMapping("/emailVerificationSuccess")
    public String emailVerificationSuccess() {
        return "member/email_verification_success";
    }

    @GetMapping("/emailVerification")
    public String emailVerification() {
        return "member/email_verification_form";
    }

    @GetMapping("/login")
    public String login() {
        return "member/login_form";
    }

    @GetMapping("/login/google")
    public String googleLogin() {
        return "redirect:/oauth2/authorization/google";
    }
    @GetMapping("/login/kakao")
    public String kakaoLogin() {
        return "redirect:/oauth2/authorization/kakao";
    }

    @GetMapping("/login/naver")
    public String naverLogin() {
        return "redirect:/oauth2/authorization/naver";
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam String username, @RequestParam String email) {
        Member member = memberService.getMemberByEmail(email);
        if (member != null && member.getUsername().equals(username)) {
            try {
                memberService.resetPassword(member);
                return ResponseEntity.ok("당신 이메일로 임시번호 발송");
            } catch (MessagingException e) {
                // 여기서 예외를 처리하거나, 적절한 로깅 또는 알림을 구현할 수 있습니다.
                e.printStackTrace();
                return ResponseEntity.badRequest().body("이메일 전송 중 오류가 발생했습니다.");
            }
        }
        return ResponseEntity.badRequest().body("Failed to reset password");
    }

    @GetMapping("/resetPassword")
    public String showResetPasswordPage() {
        return "member/reset_password"; // Thymeleaf 템플릿 이름 (reset_password.html)
    }


    // 마이페이지
    @GetMapping("/mypage")
    @PreAuthorize("isAuthenticated()")
    public String showmyPage(Model model, Principal principal) {
        Member member = memberService.getmember(principal.getName());
        System.out.println("====================" + principal.getName());

        if (member == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다.");
        }
        model.addAttribute("member", member);

        Long reviewCount= reviewService.getReivewCount(member);
        model.addAttribute("reviewCount", reviewCount);

        return "member/my_page";
    }
}
