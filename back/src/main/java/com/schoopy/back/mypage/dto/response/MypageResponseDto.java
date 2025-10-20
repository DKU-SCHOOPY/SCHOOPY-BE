package com.schoopy.back.mypage.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.user.entity.UserEntity;

import lombok.Getter;

@Getter
public class MypageResponseDto extends ResponseDto{

    private UserEntity user;
    private boolean kakaoLogin;
    private boolean naverLogin;

    private MypageResponseDto(UserEntity user) {
        super();
        this.user = user;

        if(user.getKakaoId() != null) {
            this.kakaoLogin = true;
        } else {
            this.kakaoLogin = false;
        }
        if(user.getNaverId() != null) {
            this.naverLogin = true;
        } else {
            this.naverLogin = false;
        }
    }

    public static ResponseEntity<MypageResponseDto> success(UserEntity user) {
        MypageResponseDto responseBody = new MypageResponseDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
