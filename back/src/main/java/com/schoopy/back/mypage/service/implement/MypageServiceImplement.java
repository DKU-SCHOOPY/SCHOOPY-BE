package com.schoopy.back.mypage.service.implement;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.mypage.dto.request.ChangeDeptRequestDto;
import com.schoopy.back.mypage.dto.request.ChangePhoneNumRequestDto;
import com.schoopy.back.mypage.dto.request.CouncilMypageRequestDto;
import com.schoopy.back.mypage.dto.request.MypageRequestDto;
import com.schoopy.back.mypage.dto.response.ChangeDeptResponseDto;
import com.schoopy.back.mypage.dto.response.ChangePhoeNumResponseDto;
import com.schoopy.back.mypage.dto.response.CouncilMypageResponseDto;
import com.schoopy.back.mypage.dto.response.MypageResponseDto;
import com.schoopy.back.user.entity.UserEntity;
import com.schoopy.back.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import com.schoopy.back.mypage.service.MypageService;

@Service
@RequiredArgsConstructor
public class MypageServiceImplement implements MypageService{

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super MypageResponseDto> printMypage(MypageRequestDto dto) {
        UserEntity user = userRepository.findByStudentNum(dto.getStudentNum());

        if(user == null)
            return ResponseDto.databaseError();
        

        return MypageResponseDto.success(user);
    }

    @Override
    public ResponseEntity<? super ChangeDeptResponseDto> changeDept(ChangeDeptRequestDto dto) {
        UserEntity userEntity = userRepository.findByStudentNum(dto.getStudentNum());
        if (userEntity == null) return ResponseDto.badRequest();

        try {
            userEntity.setDepartment(dto.getDepartment());
            userRepository.save(userEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ChangeDeptResponseDto.success();
    }

    @Override
    public ResponseEntity<? super ChangePhoeNumResponseDto> changePhoneNum(ChangePhoneNumRequestDto dto) {
        UserEntity userEntity = userRepository.findByStudentNum(dto.getStudentNum());
        if (userEntity == null) return ResponseDto.badRequest();
        try {
            userEntity.setPhoneNum(dto.getPhoneNum());
            userRepository.save(userEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ChangePhoeNumResponseDto.success();
    }

    @Override
    public ResponseEntity<? super CouncilMypageResponseDto> printCouncilMypage(CouncilMypageRequestDto dto) {

        List<UserEntity> councilMembers;
        try {
            councilMembers = userRepository.findByDepartment(dto.getDepartment());
            if (councilMembers == null) return ResponseDto.badRequest();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return CouncilMypageResponseDto.success(councilMembers);
    }
}
