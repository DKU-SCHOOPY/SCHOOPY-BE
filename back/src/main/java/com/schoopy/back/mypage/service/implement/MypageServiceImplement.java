package com.schoopy.back.mypage.service.implement;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.mypage.dto.request.ChangeCouncilPeeRequestDto;
import com.schoopy.back.mypage.dto.request.ChangeCouncilPeeRequestRequestDto;
import com.schoopy.back.mypage.dto.request.ChangeDeptRequestDto;
import com.schoopy.back.mypage.dto.request.ChangeEnrollRequestDto;
import com.schoopy.back.mypage.dto.request.ChangeEnrollRequestRequestDto;
import com.schoopy.back.mypage.dto.request.ChangePhoneNumRequestDto;
import com.schoopy.back.mypage.dto.request.CouncilMypageRequestDto;
import com.schoopy.back.mypage.dto.request.MypageRequestDto;
import com.schoopy.back.mypage.dto.response.ChangeCouncilPeeResponseDto;
import com.schoopy.back.mypage.dto.response.ChangeDeptResponseDto;
import com.schoopy.back.mypage.dto.response.ChangeEnrollRequestResponseDto;
import com.schoopy.back.mypage.dto.response.ChangeEnrollResponseDto;
import com.schoopy.back.mypage.dto.response.ChangePhoeNumResponseDto;
import com.schoopy.back.mypage.dto.response.CouncilMypageResponseDto;
import com.schoopy.back.mypage.dto.response.MypageResponseDto;
import com.schoopy.back.user.entity.PresidentEntity;
import com.schoopy.back.user.entity.UserEntity;
import com.schoopy.back.user.repository.PresidentRepository;
import com.schoopy.back.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import com.schoopy.back.mypage.service.MypageService;
import com.schoopy.back.notice.entity.NoticeEntity;
import com.schoopy.back.notice.repository.NoticeRepository;

@Service
@RequiredArgsConstructor
public class MypageServiceImplement implements MypageService{

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final PresidentRepository presidentRepository;

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
        boolean SW;
        try {
            if ((dto.getDepartment().equals("SW융합대학"))) {
                councilMembers = userRepository.findAll();
                SW = true;
            }else{
                councilMembers = userRepository.findByDepartment(dto.getDepartment());
                SW = false;
            }
            if (councilMembers == null) return ResponseDto.badRequest();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return CouncilMypageResponseDto.success(councilMembers, SW);
    }

    @Override
    public ResponseEntity<? super ChangeEnrollResponseDto> changeEnroll(ChangeEnrollRequestDto dto) {
        String studentNum = dto.getStudentNum();

        UserEntity userEntity = userRepository.findByStudentNum(studentNum);
        if (userEntity == null) return ResponseDto.badRequest();

        try {
            if(userEntity.isEnrolled()) {
                userEntity.setEnrolled(false);
            } else {
                userEntity.setEnrolled(true);
            }
            userRepository.save(userEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ChangeEnrollResponseDto.success();
    }

    @Override
    public ResponseEntity<? super ChangeCouncilPeeResponseDto> changeCouncilPee(ChangeCouncilPeeRequestDto dto) {

        UserEntity userEntity = userRepository.findByStudentNum(dto.getStudentNum());
        if (userEntity == null) return ResponseDto.badRequest();

        try {
            if(dto.getDepartment().equals("SW융합대학")) {
                if(userEntity.isCouncilPee()) {
                    userEntity.setCouncilPee(false);
                } else {
                    userEntity.setCouncilPee(true);
                }
            } else {
                if(userEntity.isDepartmentCouncilPee()) {
                    userEntity.setDepartmentCouncilPee(false);
                } else {
                    userEntity.setDepartmentCouncilPee(true);
                }   
            }
            userRepository.save(userEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ChangeCouncilPeeResponseDto.success();
    }

    @Override
    public ResponseEntity<? super ChangeCouncilPeeResponseDto> changeCouncilPeeRequest(ChangeCouncilPeeRequestRequestDto dto) {
        UserEntity userEntity = userRepository.findByStudentNum(dto.getStudentNum());

        if (userEntity == null) return ResponseDto.badRequest();

        String title = "학생회비 납부 인증 요청";
        String message = userEntity.getName() + "학생이 학생회비를 납부 완료하였습니다.\n 납부 여부를 확인 후 승인 부탁드립니다.";
        String department;
        if(dto.isSW()) {
            department = "SW융합대학";
        } else {
            department = userEntity.getDepartment();
        }
        PresidentEntity president = presidentRepository.findByDepartment(department);
        UserEntity presidentUser = userRepository.findByStudentNum(president.getStudentNum());
        NoticeEntity notice = new NoticeEntity(userEntity, presidentUser, "Crequest",title, message, true);
        noticeRepository.save(notice);

        return ChangeCouncilPeeResponseDto.success();

    }

    @Override
    public ResponseEntity<? super ChangeEnrollRequestResponseDto> changeEnrollRequest(ChangeEnrollRequestRequestDto dto) {

        UserEntity userEntity = userRepository.findByStudentNum(dto.getStudentNum());

        String title = "재학여부 인증 요청";
        String message = userEntity.getName() + "학생이 재학 여부 변경을 신청했습니다. \n재적 상태 확인 후 승인 부탁드립니다.";
        String department;
        department = userEntity.getDepartment();
        PresidentEntity president = presidentRepository.findByDepartment(department);
        UserEntity presidentUser = userRepository.findByStudentNum(president.getStudentNum());
        NoticeEntity notice = new NoticeEntity(userEntity, presidentUser, "Erequest",title, message, true);
        noticeRepository.save(notice);
        
        return ChangeEnrollRequestResponseDto.success();
    }
}