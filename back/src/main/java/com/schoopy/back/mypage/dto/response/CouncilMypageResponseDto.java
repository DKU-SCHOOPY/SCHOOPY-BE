package com.schoopy.back.mypage.dto.response;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.user.entity.UserEntity;

import lombok.Getter;

@Getter
public class CouncilMypageResponseDto extends ResponseDto{
    List<UserEntity> councilMembers;
    boolean SW;

    private CouncilMypageResponseDto(List<UserEntity> councilMembers, boolean SW) {
        super();
        this.councilMembers = councilMembers;
        this.SW = SW;
    }

    public static ResponseEntity<CouncilMypageResponseDto> success(List<UserEntity> councilMembers, boolean SW) {
        CouncilMypageResponseDto responseBody = new CouncilMypageResponseDto(councilMembers, SW);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
