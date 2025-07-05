package com.schoopy.back.user.service.implement;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoopy.back.user.entity.UserEntity;
import com.schoopy.back.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImplement extends DefaultOAuth2UserService{

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();
        
        try {
            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()) );
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserEntity userEntity = null;
        String userId = null;

        if(oauthClientName.equals("Kakao")) {
            userId = "kakao_" + oAuth2User.getAttributes().get("id");
        }else if(oauthClientName.equals("Naver")) {
            Map<String, String> responseMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
            userId = "naver_" + responseMap.get("id");
            String email = responseMap.get("email");
            String birthyear = responseMap.get("birthyear");    // "2000"
            String birthday = responseMap.get("birthday");      // "01-10"

            // 하이픈 제거: "0110"
            String birthdayFormatted = birthday.replace("-", "");

            // 전체 조합: "20000110"
            String fullBirthDay = birthyear + birthdayFormatted;

            String gender = (responseMap.get("gender").equals("M")) ? "male" : "female";

            String phoneNum = responseMap.get("mobile"); // "010-1234-5678"
            String phoneNumFormatted = phoneNum.replace("-", ""); // "01012345678"

            String name = responseMap.get("name");
        }

        return oAuth2User;
    }
}