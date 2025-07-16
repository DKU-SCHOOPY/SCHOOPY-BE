package com.schoopy.back.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoopy.back.user.dto.request.*;
import com.schoopy.back.user.dto.response.*;
import com.schoopy.back.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schoopy/v1/auth")
@Tag(name="User", description = "사용자 관련 API")
public class UserController {

    private final UserService userService;

    @PostMapping("/email-check")
    @Operation(summary = "이메일 확인", description = "이메일 확인")
    public ResponseEntity<? super EmailCheckResponseDto> emailCheck (
        @RequestBody @Valid EmailCheckRequestDto requestBody
    ) {
        ResponseEntity<? super EmailCheckResponseDto> response = userService.emailCheck(requestBody);
        return response;
    }
    
    @PostMapping("/email-certification")
    @Operation(summary = "이메일 인증", description = "이메일 인증")
    public  ResponseEntity<? super EmailCertificationResponseDto> emailCertification (
        @RequestBody @Valid EmailCertificationRequestDto requestBody
        ) {
            ResponseEntity<? super EmailCertificationResponseDto> response = userService.emailCertification(requestBody);
            return response;
        }

    @PostMapping("/check-certification")
    @Operation(summary = "확인 인증", description = "확인 인증")
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification (
        @RequestBody @Valid CheckCertificationRequestDto requestBody
    ) {
        ResponseEntity<? super CheckCertificationResponseDto> response = userService.checkCertification(requestBody);
        return response;
    }
    
    @PostMapping("/sign-up")
    @Operation(summary = "가입", description = "회원가입")
    public ResponseEntity<? super SignUpResponseDto> signUp(
        @RequestBody @Valid SignUpRequestDto requestBody
    ) {
        ResponseEntity<? super SignUpResponseDto> response = userService.signUp(requestBody);
        return response;
    }

    @PostMapping("/sign-in")
    @Operation(summary = "로그인", description = "로그인")
    public ResponseEntity<? super SignInResponseDto> signIn(
        @RequestBody @Valid SignInRequestDto requestBody
    ) {
        ResponseEntity<? super SignInResponseDto> response = userService.signIn(requestBody);
        return response;
    }

    @PostMapping("/mypage")
    @Operation(summary = "마이페이지", description = "내 정보 조회")
    public ResponseEntity<? super MypageResponseDto> mypage(
        @RequestBody @Valid MypageRequestDto requestBody
    ){
        ResponseEntity<? super MypageResponseDto> response = userService.printMypage(requestBody);
        return response;
    }

    @PostMapping("/change-dept")
    @Operation(summary = "학과 변경", description = "학과 변경")
    public ResponseEntity<? super ChangeDeptResponseDto> changeDept(
        @RequestBody @Valid ChangeDeptRequestDto requestBody
    ){
        ResponseEntity<? super ChangeDeptResponseDto> response = userService.changeDept(requestBody);
        return response;
    }

    @PostMapping("/change-phone-num")
    @Operation(summary = "전화번호 변경", description = "전화번호 변경")
    public ResponseEntity<? super ChangePhoeNumResponseDto> changePhoneNum(
        @RequestBody @Valid ChangePhoneNumRequestDto requestBody
    ){
        ResponseEntity<? super ChangePhoeNumResponseDto> response = userService.changePhoneNum(requestBody);
        return response;
    }
}