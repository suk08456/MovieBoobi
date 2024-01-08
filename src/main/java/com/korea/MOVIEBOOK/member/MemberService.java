package com.korea.MOVIEBOOK.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public Member create(String username, String password, String nickname, String email) {
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(passwordEncoder.encode(password));
        member.setNickname(nickname);
        member.setEmail(email);
        this.memberRepository.save(member);
        return member;
    }

    public void verifyEmail(long memberId) {
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        // memberId를 통해 특정 member 데이터 조회
        memberOpt.ifPresent(member -> { // 해당 memberId가 존재하면 데이터 가져옴
            member.setVerified(true);  // 이메일 인증 완료
            memberRepository.save(member);  // 업데이트된 사용자 저장
        });
    }
}