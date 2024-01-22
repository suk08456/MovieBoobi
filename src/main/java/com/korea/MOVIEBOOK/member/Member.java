package com.korea.MOVIEBOOK.member;

import com.korea.MOVIEBOOK.payment.Payment;
import com.korea.MOVIEBOOK.review.Review;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;


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
