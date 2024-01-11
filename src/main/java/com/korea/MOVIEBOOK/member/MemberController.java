package com.korea.MOVIEBOOK.member;

import com.korea.MOVIEBOOK.review.Review;
import com.korea.MOVIEBOOK.review.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;
    private final ReviewService reviewService;
    private final PasswordEncoder passwordEncoder;

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

    @GetMapping("/login/kakao")
    public String kakaoLogin() {
        return "redirect:/oauth2/authorization/kakao";
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
//
//        List<Review> reviewList = reviewService.getAnswerTop5LatestByUser(user);
//        model.addAttribute("answerList", answerList);
        return "member/my_page";
    }

    @GetMapping("/changePw")
    public String changePw(PasswordChangeForm passwordChangeForm) {
        return "member/changepw";
    }


    @PostMapping("/changePw")
    public String passwordChange(@Valid PasswordChangeForm passwordChangeForm, BindingResult bindingResult,
                                 Principal principal) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        GrantedAuthority authority = authentication.getAuthorities().iterator().next();

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
        return "redirect:/member/logout";
    }

    private void bindingResultReject(BindingResult bindingResult) {
        bindingResult.rejectValue("passwordConfirm", "passwordInCorrect",
                "패스워드가 일치하지 않습니다.");
    }


    @GetMapping("/changeInformation")
    public String updateNm(NicknameForm nicknameForm) {
        return "member/changeinfor";
    }

    @PostMapping("/changeInformation")
    public String updateNickname(@Valid NicknameForm nicknameForm, BindingResult bindingResult,
                                 Principal principal) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        GrantedAuthority authority = authentication.getAuthorities().iterator().next();

        if (bindingResult.hasErrors()) {
            return "member/changeinfor";
        }

        if (nicknameForm.getNewNickname().length() >= 3 || nicknameForm.getNewNickname().length() > 20) {
                Member member = memberService.findByusername(principal.getName());
                if(member == null){
                    member = memberService.findByproviderId(principal.getName());
                }
                memberService.updateNickname(member, nicknameForm.getNewNickname());
        }
        return "redirect:/member/mypage";
    }


}
