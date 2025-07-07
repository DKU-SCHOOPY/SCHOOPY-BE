package com.schoopy.back.user.controller;

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
public class UserController {

    private final UserService userService;

    @PostMapping("/email-check")
    public ResponseEntity<? super EmailCheckResponseDto> emailCheck (
        @RequestBody @Valid EmailCheckRequestDto requestBody
    ) {
        ResponseEntity<? super EmailCheckResponseDto> response = userService.emailCheck(requestBody);
        return response;
    }
    
    @PostMapping("/email-certification")
    public  ResponseEntity<? super EmailCertificationResponseDto> emailCertification (
        @RequestBody @Valid EmailCertificationRequestDto requestBody
        ) {
            ResponseEntity<? super EmailCertificationResponseDto> response = userService.emailCertification(requestBody);
            return response;
        }

    @PostMapping("/check-certification")
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification (
        @RequestBody @Valid CheckCertificationRequestDto requestBody
    ) {
        ResponseEntity<? super CheckCertificationResponseDto> response = userService.checkCertification(requestBody);
        return response;
    }
    
    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(
        @RequestBody @Valid SignUpRequestDto requestBody
    ) {
        ResponseEntity<? super SignUpResponseDto> response = userService.signUp(requestBody);
        return response;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(
        @RequestBody @Valid SignInRequestDto requestBody
    ) {
        ResponseEntity<? super SignInResponseDto> response = userService.signIn(requestBody);
        return response;
    }

    @PostMapping("/mypage")
    public ResponseEntity<? super MypageResponseDto> mypage(
        @RequestBody @Valid MypageRequestDto requestBody
    ){
        ResponseEntity<? super MypageResponseDto> response = userService.printMypage(requestBody);
        return response;
    }

    @PostMapping("/change-dept")
    public ResponseEntity<? super ChangeDeptResponseDto> changeDept(
        @RequestBody @Valid ChangeDeptRequestDto requestBody
    ){
        ResponseEntity<? super ChangeDeptResponseDto> response = userService.changeDept(requestBody);
        return response;
    }

    @PostMapping("/change-phone-num")
    public ResponseEntity<? super ChangePhoeNumResponseDto> changePhoneNum(
        @RequestBody @Valid ChangePhoneNumRequestDto requestBody
    ){
        ResponseEntity<? super ChangePhoeNumResponseDto> response = userService.changePhoneNum(requestBody);
        return response;
    }
}