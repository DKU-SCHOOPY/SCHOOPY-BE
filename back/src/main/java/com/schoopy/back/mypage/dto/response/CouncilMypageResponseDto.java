package com.schoopy.back.mypage.dto.response;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.user.entity.UserEntity;

public class CouncilMypageResponseDto extends ResponseDto{
    List<UserEntity> councilMembers;

    private CouncilMypageResponseDto(List<UserEntity> councilMembers) {
        super();
        this.councilMembers = councilMembers;
    }

    public static ResponseEntity<CouncilMypageResponseDto> success(List<UserEntity> councilMembers) {
        CouncilMypageResponseDto responseBody = new CouncilMypageResponseDto(councilMembers);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
