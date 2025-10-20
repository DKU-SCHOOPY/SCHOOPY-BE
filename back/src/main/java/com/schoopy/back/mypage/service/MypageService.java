package com.schoopy.back.mypage.service;

import org.springframework.http.ResponseEntity;
import com.schoopy.back.mypage.dto.request.*;
import com.schoopy.back.mypage.dto.response.*;
public interface MypageService {
    ResponseEntity<? super MypageResponseDto> printMypage (MypageRequestDto dto);
    ResponseEntity<? super ChangeDeptResponseDto> changeDept (ChangeDeptRequestDto dto);
    ResponseEntity<? super ChangePhoeNumResponseDto> changePhoneNum (ChangePhoneNumRequestDto dto);
    ResponseEntity<? super CouncilMypageResponseDto> printCouncilMypage (CouncilMypageRequestDto dto);
    ResponseEntity<? super ChangeEnrollResponseDto> changeEnroll (ChangeEnrollRequestDto dto);
    ResponseEntity<? super ChangeCouncilPeeResponseDto> changeCouncilPee (ChangeCouncilPeeRequestDto dto);
    ResponseEntity<? super ChangeCouncilPeeResponseDto> changeCouncilPeeRequest(ChangeCouncilPeeRequestRequestDto dto);
    ResponseEntity<? super ChangeEnrollRequestResponseDto> changeEnrollRequest(ChangeEnrollRequestRequestDto dto);
}
