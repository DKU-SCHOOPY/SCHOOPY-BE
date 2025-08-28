package com.schoopy.back.mypage.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.user.entity.UserEntity;

import lombok.Getter;

@Getter
public class MypageResponseDto extends ResponseDto{

    private String name;
    private String studentNum;
    private String department;
    private String birthDay;
    private String phoneNum;

    private MypageResponseDto(UserEntity user) {
        super();
        this.name = user.getName();
        this.studentNum = user.getStudentNum();
        this.department = user.getDepartment();
        this.birthDay = user.getBirthDay();
        this.phoneNum = user.getPhoneNum();
    }

    public static ResponseEntity<MypageResponseDto> success(UserEntity user) {
        MypageResponseDto responseBody = new MypageResponseDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
