package com.schoopy.back.user.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

public class LinkSocialResponseDto extends ResponseDto {
    private LinkSocialResponseDto() {
        super();
    }

    public static ResponseEntity<LinkSocialResponseDto> kakaoLinkSuccess() {
        return ResponseEntity.status(HttpStatus.OK).body(new LinkSocialResponseDto());
    }

    public static ResponseEntity<LinkSocialResponseDto> naverLinkSuccess() {
        return ResponseEntity.status(HttpStatus.OK).body(new LinkSocialResponseDto());
    }
}