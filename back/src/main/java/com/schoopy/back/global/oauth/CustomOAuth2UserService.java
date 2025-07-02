package com.schoopy.back.global.oauth;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.schoopy.back.user.entity.UserEntity;
import com.schoopy.back.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
    private final UserRepository userRepository;

    @SuppressWarnings("unchecked")
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = 
            new DefaultOAuth2UserService().loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthUserInfo userInfo = null;
        if ("kakao".equals(registrationId)) {
            userInfo = new KakaoUserInfo(attributes);
        } else if ("naver".equals(registrationId)) {
            userInfo = new NaverUserInfo((Map<String, Object>) attributes.get("response"));
        }

        if (userInfo == null) throw new OAuth2AuthenticationException("Provider not supported");

        String studentNum = userInfo.getId();
        String email = userInfo.getEmail();
        String name = userInfo.getName();

        UserEntity user = userRepository.findByStudentNum(studentNum);
        if (user == null) {
            user = UserEntity.builder()
                .studentNum(studentNum)
                .email(email)
                .name(name)
                .password("") // 소셜 로그인 사용자는 비밀번호 없음
                .build();
            userRepository.save(user);
        }

        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
            attributes,
            "id"
        );
    }
}
