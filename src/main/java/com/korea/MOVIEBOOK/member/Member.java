package com.korea.MOVIEBOOK.member;

import com.korea.MOVIEBOOK.payment.Payment;
import com.korea.MOVIEBOOK.review.Review;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    @Column(unique = true)
    private String username; // 유저 아이디

    private String password; // 비밀번호

    @Column(unique = true)
    private String email; // 이메일

    private String nickname; // 닉네임

    private String profileImage = "/Member/defaultImage.jpg"; // 프로필 이미지

    private boolean isVerified = false;  // 이메일 인증 상태를 나타내는 필드

    private String provider;

    private String providerId;

//    private String photo;

    @OneToMany(mappedBy = "member")
    private List<Payment> paymentList;

    @OneToMany(mappedBy = "member")
    private List<Review> reviewList;
}



//    @PostMapping("/changeNickname")
//    public String nicknameChange(@RequestParam("newNickname") String newNickname, Principal principal) {
//        // 새로운 nickname이 유효한지 검증
//        // 여기서는 간단하게 길이만 확인하는 예제
//        if (newNickname.length() < 3 || newNickname.length() > 20) {
//            // 유효하지 않은 경우에 대한 처리
//            return "redirect:/mypage/profile?error";
//        }
//
//        // 현재 로그인한 사용자의 정보를 가져옴
//        String username = principal.getName();
//        Member member = memberService.findByUsername(username);
//
//        // nickname 변경
//        member.setNickname(newNickname);
//        memberService.save(member);
//
//        // 변경 후 마이페이지로 리다이렉트
//        return "redirect:/mypage/profile?success";
//    }
//else {
//        Customer customer = customerService.findByusername(principal.getName());
//        if (passwordEncoder.matches(passwordChangeForm.getOldPassword(), customer.getPassword())) {
//        customerService.changePassword(customer, passwordChangeForm.getOldPassword());
//        } else {
//        bindingResultReject(bindingResult);
//        return "member/changePwForm";
//        }
//        }
