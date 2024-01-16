package com.korea.MOVIEBOOK.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberOpt = memberRepository.findByUsername(username);
// username을 사용하여 Member 데이터를 조회
        if (memberOpt.isEmpty() || !memberOpt.get().isVerified()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        } // 사용자가 존재하지 않거나 인증되지 않은 경우 예외 처리
        Member member = memberOpt.get();
// 조회한 meber객체 가져옴
        List<GrantedAuthority> authorities = new ArrayList<>();
// 사용자 권한을 나타내는 리스트 생성
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.MEMBER.getValue()));
        } // username이 admin인 경우 admin권한 member일 경우 member권한 부여
        return new User(member.getUsername(), member.getPassword(), authorities);
// 사용자의 인증 및 권한 정보 User 객체 반환
    }
}