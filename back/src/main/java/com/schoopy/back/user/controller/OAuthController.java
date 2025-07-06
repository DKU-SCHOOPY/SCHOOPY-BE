package com.schoopy.back.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schoopy.back.user.dto.response.SignInResponseDto;
import com.schoopy.back.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schoopy/v1/oauth")
public class OAuthController {
    private final UserService userService;

    @GetMapping("/naver/callback")
    public ResponseEntity<? super SignInResponseDto> naverCallback(
        @RequestParam("code") String code,
        @RequestParam("state") String state
    ) {
        return userService.naverLogin(code, state);
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<? super SignInResponseDto> kakaoCallback(
        @RequestParam("code") String code
    ) {
        return userService.kakaoLogin(code);
    }
}
