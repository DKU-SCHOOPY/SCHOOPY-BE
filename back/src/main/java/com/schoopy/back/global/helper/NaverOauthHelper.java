package com.schoopy.back.global.helper;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NaverOauthHelper {

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAccessToken(String code, String state) {
        String tokenUri = "https://nid.naver.com/oauth2.0/token" +
                "?grant_type=authorization_code" +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&code=" + code +
                "&state=" + state;

        Map<String, Object> tokenResponse = restTemplate.getForObject(tokenUri, Map.class);
        return (String) tokenResponse.get("access_token");
    }

    public String getUserIdFromToken(String accessToken) {
        String userInfoUri = "https://openapi.naver.com/v1/nid/me";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
            userInfoUri, HttpMethod.GET, entity, Map.class
        );

        Map<String, Object> body = response.getBody();
        Map<String, Object> userInfo = (Map<String, Object>) body.get("response");
        return (String) userInfo.get("id");
    }
}
