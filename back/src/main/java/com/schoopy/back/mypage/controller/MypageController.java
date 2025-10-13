package com.schoopy.back.mypage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoopy.back.mypage.dto.request.*;
import com.schoopy.back.mypage.dto.response.*;
import com.schoopy.back.mypage.service.MypageService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    private final MypageService mypageService;

    @PostMapping("/all/check")
    @Operation(summary = "마이페이지", description = "내 정보 조회")
    public ResponseEntity<? super MypageResponseDto> mypage(
        @RequestBody @Valid MypageRequestDto requestBody
    ){
        ResponseEntity<? super MypageResponseDto> response = mypageService.printMypage(requestBody);
        return response;
    }

    @PostMapping("/student/change-dept")
    @Operation(summary = "학과 변경", description = "학과 변경")
    public ResponseEntity<? super ChangeDeptResponseDto> changeDept(
        @RequestBody @Valid ChangeDeptRequestDto requestBody
    ){
        ResponseEntity<? super ChangeDeptResponseDto> response = mypageService.changeDept(requestBody);
        return response;
    }

    @PostMapping("/student/change-phone-num")
    @Operation(summary = "전화번호 변경", description = "전화번호 변경")
    public ResponseEntity<? super ChangePhoeNumResponseDto> changePhoneNum(
        @RequestBody @Valid ChangePhoneNumRequestDto requestBody
    ){
        ResponseEntity<? super ChangePhoeNumResponseDto> response = mypageService.changePhoneNum(requestBody);
        return response;
    }

    @PostMapping("/council/check")
    @Operation(summary = "학생회 마이페이지", description = "학과 정보 조회")
    public ResponseEntity<? super CouncilMypageResponseDto> coucilMypage(
        @RequestBody @Valid CouncilMypageRequestDto requestBody
    ){
        ResponseEntity<? super CouncilMypageResponseDto> response = mypageService.printCouncilMypage(requestBody);
        return response;
    }

    @PostMapping("/council/change-enroll")
    @Operation(summary = "재학여부 변경", description = "재학여부 변경")
    public ResponseEntity<? super ChangeEnrollResponseDto> changeEnroll(
        @RequestBody @Valid ChangeEnrollRequestDto requestBody
    ){
        ResponseEntity<? super ChangeEnrollResponseDto> response = mypageService.changeEnroll(requestBody);
        return response;
    }

    @PostMapping("/council/change-council-pee")
    @Operation(summary = "학생회비 납부 여부 변경", description = "학생회비 납부 여부 변경")
    public ResponseEntity<? super ChangeCouncilPeeResponseDto> changeCouncilPee(
        @RequestBody @Valid ChangeCouncilPeeRequestDto requestBody
    ){
        ResponseEntity<? super ChangeCouncilPeeResponseDto> response = mypageService.changeCouncilPee(requestBody);
        return response;
    }
}
