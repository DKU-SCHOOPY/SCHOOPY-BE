package com.schoopy.back.user.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

import lombok.Getter;

@Getter
public class CheckDepartmentForCouncilResponseDto extends ResponseDto{

    private String department;

    private CheckDepartmentForCouncilResponseDto(String department) {
        super();
        this.department = department;
    }

    public static ResponseEntity<CheckDepartmentForCouncilResponseDto> success(String department) {
        CheckDepartmentForCouncilResponseDto responseBody = new CheckDepartmentForCouncilResponseDto(department);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
