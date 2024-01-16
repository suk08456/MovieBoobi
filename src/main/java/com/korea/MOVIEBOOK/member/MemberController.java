package com.korea.MOVIEBOOK.member;

import com.korea.MOVIEBOOK.payment.Payment;
import com.korea.MOVIEBOOK.payment.PaymentService;
import com.korea.MOVIEBOOK.review.Review;
import com.korea.MOVIEBOOK.review.ReviewService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.member;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;
    private final ReviewService reviewService;
    private final PasswordEncoder passwordEncoder;
    private final PaymentService paymentService;

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

    @PostMapping("/findUsername")
    public ResponseEntity<String> findUsername(@RequestParam String email) {
        Member member = memberService.getMemberByEmail(email);
        if (member != null) {
            try {
                emailService.sendTemporaryUsername(member.getEmail(), member.getUsername());
                return ResponseEntity.ok("이메일로 아이디를 발송");
            } catch (MessagingException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body("이메일 전송 중 오류 발생");
            }
        }
        return ResponseEntity.badRequest().body("해당 이메일로 등록된 아이디 없음");
    }


    @GetMapping("/resetPassword")
    public String showResetPasswordPage() {
        return "member/reset_password"; // Thymeleaf 템플릿 이름 (reset_password.html)
    }

    @GetMapping("/findUsername")
    public String findUsernamePage() {
        return "/member/find_username";
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

        Long reviewCount = reviewService.getReivewCount(member);
        model.addAttribute("reviewCount", reviewCount);
        model.addAttribute("parameter", 0);


        List<Payment> payments = this.paymentService.findPaymentListByMember(member);
        long sum = 0;

        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).getContent().contains("충전")) {
                sum += Long.valueOf(payments.get(i).getPaidAmount());
            } else {
                sum -= Long.valueOf(payments.get(i).getPaidAmount());
            }
        }

        model.addAttribute("sum", sum);


//        Page<Payment> paging = this.paymentService.getPaymentsByMember(member, page);


//        List<Review> reviewList = reviewService.getAnswerTop5LatestByUser(user);
//        model.addAttribute("answerList", answerList);
        return "member/my_page";
    }

    @GetMapping("/changeInformation")
    public String updateNm(Model model, NicknameForm nicknameForm, Principal principal) {

        Member member = memberService.findByusername(principal.getName());
        if (member == null) {
            member = memberService.findByproviderId(principal.getName());
        }
        List<Payment> payments = this.paymentService.findPaymentListByMember(member);
        long sum = 0;

        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).getContent().contains("충전")) {
                sum += Long.valueOf(payments.get(i).getPaidAmount());
            } else {
                sum -= Long.valueOf(payments.get(i).getPaidAmount());
            }
        }
        model.addAttribute("parameter", 1);
        model.addAttribute("member", member);
        model.addAttribute("sum", sum);
        return "member/changeinfor";
    }

    @PostMapping("/changeInformation")
    public String updateNickname(Model model, @Valid NicknameForm nicknameForm, BindingResult bindingResult,
                                 Principal principal) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        GrantedAuthority authority = authentication.getAuthorities().iterator().next();
        model.addAttribute("parameter", 1);


        if (bindingResult.hasErrors()) {
            return "member/changeinfor";
        }

        if (nicknameForm.getNewNickname().length() >= 3 || nicknameForm.getNewNickname().length() > 20) {
            Member member = memberService.findByusername(principal.getName());
            if (member == null) {
                member = memberService.findByproviderId(principal.getName());
            }
            memberService.updateNickname(member, nicknameForm.getNewNickname());
        }
        model.addAttribute("member", member);

        return "redirect:/member/mypage";
    }

    @GetMapping("/changePw")
    public String changePw(Model model, PasswordChangeForm passwordChangeForm, Principal principal) {
        Member member = memberService.findByusername(principal.getName());
        if (member == null) {
            member = memberService.findByproviderId(principal.getName());
        }
        List<Payment> payments = this.paymentService.findPaymentListByMember(member);
        long sum = 0;

        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).getContent().contains("충전")) {
                sum += Long.valueOf(payments.get(i).getPaidAmount());
            } else {
                sum -= Long.valueOf(payments.get(i).getPaidAmount());
            }
        }

        model.addAttribute("parameter", 2);
        model.addAttribute("member", member);
        model.addAttribute("sum", sum);

        return "member/changepw";
    }


    @PostMapping("/changePw")
    public String passwordChange(Model model, @Valid PasswordChangeForm passwordChangeForm, BindingResult bindingResult,
                                 Principal principal) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        GrantedAuthority authority = authentication.getAuthorities().iterator().next();
        model.addAttribute("parameter", 2);

        if (bindingResult.hasErrors()) {
            return "member/changepw";
        }

        if (passwordChangeForm.getPassword().equals(passwordChangeForm.getPasswordConfirm())) {
            if (authority.getAuthority().equals("ROLE_MEMBER")) {
                Member member = memberService.findByusername(principal.getName());
                if (passwordEncoder.matches(passwordChangeForm.getOldPassword(), member.getPassword())) {
                    memberService.changePassword(member, passwordChangeForm.getPassword());
                } else {
                    bindingResultReject(bindingResult);
                    return "member/changepw";
                }
            }
        } else {
            bindingResultReject(bindingResult);
            return "member/changepw";
        }

        model.addAttribute("member", member);
        return "redirect:/member/logout";
    }


    @GetMapping("/deleteForm")
    public String deleteForm(PasswordResetForm passwordResetForm, Principal principal, Model model) {
        Member member = memberService.findByusername(principal.getName());
        if (member == null) {
            member = memberService.findByproviderId(principal.getName());
        }
        List<Payment> payments = this.paymentService.findPaymentListByMember(member);
        long sum = 0;

        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).getContent().contains("충전")) {
                sum += Long.valueOf(payments.get(i).getPaidAmount());
            } else {
                sum -= Long.valueOf(payments.get(i).getPaidAmount());
            }
        }

//        model.addAttribute("parameter", 2);
        model.addAttribute("member", member);
        model.addAttribute("sum", sum);
        return "member/delete_form";
    }
//
//    @PostMapping("/delete")
//    public String delete(@RequestParam("id") Long id,
//                         @Valid PasswordResetForm passwordResetForm,
//                         BindingResult bindingResult,
//                         Model model) {
//
//        if (bindingResult.hasErrors()) {
//            return "member/delete_form";
//        }
//
//        // 회원 ID를 이용하여 회원 조회
//        Optional<Member> optionalMember = memberService.findById(id);
//
//        if (optionalMember.isEmpty()) {
//            // 회원이 존재하지 않으면 에러 처리
//            bindingResult.rejectValue("memberId", "error.member", "회원을 찾을 수 없습니다.");
//            return "member/delete_form";
//        }
//
//        Member member = optionalMember.get(); // Optional에서 값을 가져옴
//
//        // 비밀번호 확인
//        if (passwordResetForm.getPassword().equals(passwordResetForm.getPasswordConfirm())) {
//            // 입력된 비밀번호와 회원의 비밀번호가 일치하는지 확인
//            if (passwordEncoder.matches(passwordResetForm.getPassword(), member.getPassword())) {
//                // 일치하면 회원 삭제
//                memberService.deleteMember(member);
//                return "redirect:/";
//            } else {
//                // 비밀번호 불일치 시 에러 처리
//                bindingResult.rejectValue("password", "error.password", "비밀번호가 일치하지 않습니다.");
//                return "member/delete_form";
//            }
//        } else {
//            // 비밀번호와 비밀번호 확인이 일치하지 않을 경우 에러 처리
//            bindingResult.rejectValue("passwordConfirm", "error.passwordConfirm", "비밀번호 확인이 일치하지 않습니다.");
//            return "member/delete_form";
//        }
//    }

   @PostMapping("/delete")
    public String delete(Principal principal, @Valid PasswordResetForm passwordResetForm,
                         BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "member/delete_form";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("parameter", 3);

        if (passwordResetForm.getPassword().equals(passwordResetForm.getPasswordConfirm())) {
            Member member = memberService.findByusername(principal.getName());
            if (member == null) {
                member = memberService.findByproviderId(principal.getName());
            }
            if (passwordEncoder.matches(passwordResetForm.getPassword(), member.getPassword())) {
                memberService.deleteMember(member);
                return "redirect:/";
            } else {
                bindingResultReject(bindingResult);
                return "member/delete_form";
            }
        } else {
            bindingResultReject(bindingResult);
            return "member/delete_form";
        }
    }


    private void bindingResultReject(BindingResult bindingResult) {
        bindingResult.rejectValue("passwordConfirm", "passwordInCorrect",
                "패스워드가 일치하지 않습니다.");
    }
}
