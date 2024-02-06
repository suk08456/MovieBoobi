package com.korea.MOVIEBOOK.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Oauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("Attributes: " + oAuth2User.getAttributes().toString());

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = "";

        if (provider.equals("kakao")) {
            Long id = oAuth2User.getAttribute("id");
            providerId = String.valueOf(id);
        } else if (provider.equals("google")) {
            providerId = oAuth2User.getAttribute("sub");
        } else if (provider.equals("naver")) {
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> naverResponse = (Map<String, Object>) attributes.get("response");
            if (naverResponse != null) {
                providerId = (String) naverResponse.get("id");
            }
        }

        String username = provider + "_" + providerId;
        Optional<Member> _member = memberRepository.findByUsername(username);
        Member member;

        if (_member.isPresent()) {
            member = _member.get();
            // 기존 사용자 정보 업데이트 로직 (필요한 경우 추가)
        } else {
            member = new Member();
            member.setUsername(username);
            member.setProvider(provider);
            member.setProviderId(providerId);
            member.setNickname(oAuth2User.getAttribute("name"));
            member.setEmail(oAuth2User.getAttribute("email"));
            String defaultImageUrl = getDefaultImageUrl(provider);
            member.setProfileImage(defaultImageUrl);

            if ("naver".equals(provider)) {
                Map<String, Object> naverResponse = (Map<String, Object>) oAuth2User.getAttributes().get("response");
                if (naverResponse != null) {
                    member.setNickname((String) naverResponse.get("nickname"));
                    member.setProfileImage((String) naverResponse.get("profile_image"));
                    member.setEmail((String) naverResponse.get("email"));
                }
            } else {
                Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttributes().get("properties");
                if (properties != null) {
                    member.setNickname((String) properties.get("nickname"));
                    member.setProfileImage((String) properties.get("profile_image"));
                }
            }
            memberRepository.save(member);
        }
        return oAuth2User;
    }

    private String getDefaultImageUrl(String provider) {
        if (provider.equals("google")) {
        }
        return "/Member/defaultImage.jpg";
    }
}