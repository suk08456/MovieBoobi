package com.korea.MOVIEBOOK.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

    private String profileImage = "/Member/defaultImage"; // 프로필 이미지

    private boolean isVerified = false;  // 이메일 인증 상태를 나타내는 필드



}
