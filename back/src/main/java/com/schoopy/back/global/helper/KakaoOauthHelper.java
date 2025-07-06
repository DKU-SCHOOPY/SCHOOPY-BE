package com.schoopy.back.global.helper;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoOauthHelper {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    // ✅ 액세스 토큰만 반환
    public String getAccessToken(String code) {
        String tokenUri = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=authorization_code" +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&redirect_uri=" + redirectUri +
                "&code=" + code;

        HttpEntity<String> tokenRequest = new HttpEntity<>(body, headers);
        ResponseEntity<Map> tokenResponse = restTemplate.exchange(
                tokenUri,
                HttpMethod.POST,
                tokenRequest,
                Map.class
        );

        return (String) tokenResponse.getBody().get("access_token");
    }

    // ✅ 액세스 토큰 기반으로 사용자 ID 반환
    public String getUserIdFromToken(String accessToken) {
        String userInfoUri = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<?> userInfoRequest = new HttpEntity<>(headers);
        ResponseEntity<Map> userInfoResponse = restTemplate.exchange(
                userInfoUri,
                HttpMethod.GET,
                userInfoRequest,
                Map.class
        );

        Map<String, Object> userInfo = userInfoResponse.getBody();
        return String.valueOf(userInfo.get("id")); // Long 타입을 문자열로 변환
    }

    // ✅ code로부터 직접 사용자 ID까지 가져오는 단축 메서드
    public String getKakaoUserId(String code) {
        String accessToken = getAccessToken(code);
        return getUserIdFromToken(accessToken);
    }
}
