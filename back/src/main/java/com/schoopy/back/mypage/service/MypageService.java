package com.schoopy.back.mypage.service;

import org.springframework.http.ResponseEntity;
import com.schoopy.back.mypage.dto.request.MypageRequestDto;
import com.schoopy.back.mypage.dto.request.ChangeDeptRequestDto;
import com.schoopy.back.mypage.dto.request.ChangePhoneNumRequestDto;
import com.schoopy.back.mypage.dto.request.CouncilMypageRequestDto;
import com.schoopy.back.mypage.dto.response.MypageResponseDto;  
import com.schoopy.back.mypage.dto.response.ChangeDeptResponseDto;
import com.schoopy.back.mypage.dto.response.ChangePhoeNumResponseDto;
import com.schoopy.back.mypage.dto.response.CouncilMypageResponseDto;

public interface MypageService {
    ResponseEntity<? super MypageResponseDto> printMypage (MypageRequestDto dto);
    ResponseEntity<? super ChangeDeptResponseDto> changeDept (ChangeDeptRequestDto dto);
    ResponseEntity<? super ChangePhoeNumResponseDto> changePhoneNum (ChangePhoneNumRequestDto dto);
    ResponseEntity<? super CouncilMypageResponseDto> printCouncilMypage (CouncilMypageRequestDto dto);
}
