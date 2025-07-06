package com.schoopy.back.user.service;

import org.springframework.http.ResponseEntity;

import com.schoopy.back.user.dto.request.*;

import com.schoopy.back.user.dto.response.*;

public interface UserService {
    ResponseEntity<? super EmailCheckResponseDto> emailCheck(EmailCheckRequestDto dto);
    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);
    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto);
    ResponseEntity<? super SignUpResponseDto> signUp (SignUpRequestDto dto);
    ResponseEntity<? super SignInResponseDto> signIn (SignInRequestDto dto);
    ResponseEntity<? super MypageResponseDto> printMypage (MypageRequestDto dto);
    ResponseEntity<? super ChangeDeptResponseDto> changeDept (ChangeDeptRequestDto dto);
    ResponseEntity<? super ChangePhoeNumResponseDto> changePhoneNum (ChangePhoneNumRequestDto dto);
    ResponseEntity<? super SignInResponseDto> naverLogin(String code, String state);
    ResponseEntity<? super SignInResponseDto> kakaoLogin(String code);
}