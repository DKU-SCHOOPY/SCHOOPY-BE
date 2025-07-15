package com.schoopy.back.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schoopy.back.user.dto.request.LinkKakaoRequestDto;
import com.schoopy.back.user.dto.request.LinkNaverRequestDto;
import com.schoopy.back.user.dto.response.LinkSocialResponseDto;
import com.schoopy.back.user.dto.response.SignInResponseDto;
import com.schoopy.back.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schoopy/v1/oauth")
@Tag(name="OAuth", description = "소셜 로그인 관련 API")
public class OAuthController {
    private final UserService userService;

    @GetMapping("/naver/callback")
    @Operation(summary = "네이버콜백", description = "네이버콜백설명")
    public ResponseEntity<? super SignInResponseDto> naverCallback(
        @RequestParam("code") String code,
        @RequestParam("state") String state
    ) {
        return userService.naverLogin(code, state);
    }

    @GetMapping("/kakao/callback")
    @Operation(summary = "카카오콜백", description = "카카오콜백 설명")
    public ResponseEntity<? super SignInResponseDto> kakaoCallback(
        @RequestParam("code") String code
    ) {
        return userService.kakaoLogin(code);
    }

    @PostMapping("/kakao/link")
    @Operation(summary = "카카오링크", description = "카카오링크 설명")
    public ResponseEntity<? super LinkSocialResponseDto> kakaoLink(
        @RequestBody @Valid LinkKakaoRequestDto requestBody
    ) {
        ResponseEntity<? super LinkSocialResponseDto> response = userService.kakaoLink(requestBody);
        return response;
    }

    @PostMapping("/naver/link")
    @Operation(summary = "네이버링크", description = "네이버링크 설명")
    public ResponseEntity<? super LinkSocialResponseDto> naverLink(
        @RequestBody @Valid LinkNaverRequestDto requestBody
    ) {
        ResponseEntity<? super LinkSocialResponseDto> response = userService.naverLink(requestBody);
        return response;
    }
}