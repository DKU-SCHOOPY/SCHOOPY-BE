package com.schoopy.back.user.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.global.provider.EmailProvider;
import com.schoopy.back.global.provider.JwtProvider;
import com.schoopy.back.user.dto.request.*;
import com.schoopy.back.user.dto.response.*;
import com.schoopy.back.user.entity.CertificationEntity;
import com.schoopy.back.user.entity.UserEntity;
import com.schoopy.back.user.repository.CertificationRepository;
import com.schoopy.back.user.repository.UserRepository;
import com.schoopy.back.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService{
    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;

    private final JwtProvider jwtProvider;
    private final EmailProvider emailProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Override
    public ResponseEntity<? super EmailCheckResponseDto> emailCheck(EmailCheckRequestDto dto) {
        
        try {

            String studentNum = dto.getStudentNum();
            boolean isExistId = userRepository.existsByStudentNum(studentNum);
            if (isExistId) return EmailCheckResponseDto.duplicatedId();
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return EmailCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto) {
        
        try {

            String studentNum = dto.getStudentNum();

            //존재하는 이메일인지 확인
            boolean isExistId = userRepository.existsByStudentNum(studentNum);
            if (isExistId) return EmailCertificationResponseDto.duplicateId();

            //인증번호 생성
            String certificationNumber = getCertificationNumber();

            String email = studentNum + "@dankook.ac.kr";
            //메일 전송
            boolean isSuccessed = emailProvider.sendCertificationMail(email, certificationNumber);
            if (!isSuccessed) return EmailCertificationResponseDto.mailSendFail();

            //메일 전송 결과 저장
            CertificationEntity certificationEntity = new CertificationEntity(studentNum, certificationNumber);
            certificationRepository.save(certificationEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return EmailCertificationResponseDto.success();
    }

    private static String getCertificationNumber() {

        String certificationNumber = "";

        for(int count = 0; count < 4; count++) certificationNumber += (int) (Math.random() * 10);

        return certificationNumber;
    }

    @Override
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto) {
        try {

            String studentNum = dto.getStudentNum();
            String certificationNumber = dto.getCertificationNumber();

            CertificationEntity certificationEntity = certificationRepository.findBystudentNum(studentNum);
            if (certificationEntity == null) return CheckCertificationResponseDto.databaseError();

            boolean isMatched = certificationEntity.getStudentNum().equals(studentNum) && certificationEntity.getCertificationNumber().equals(certificationNumber);
            if (!isMatched) return CheckCertificationResponseDto.certificationFail();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return CheckCertificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
        
        try {

            String studentNum = dto.getStudentNum();
            boolean isExistId = userRepository.existsByStudentNum(studentNum);
            if (isExistId) return SignUpResponseDto.duplicateId();

            String certificationNumber = dto.getCertificationNumber();
            
            CertificationEntity certificationEntity = certificationRepository.findBystudentNum(studentNum);
            boolean isMatched = 
                certificationEntity.getStudentNum().equals(studentNum) && 
                certificationEntity.getCertificationNumber().equals(certificationNumber);
            if (!isMatched) return SignUpResponseDto.certificationFail();

            String password = dto.getPassword();
            if (password == null || password.isEmpty()) return SignUpResponseDto.passwordEmptyError();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);

            certificationRepository.deleteByStudentNum(studentNum);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignUpResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {

        String token = null;
        UserEntity userEntity;
        String studentNum;

        try {
            
            studentNum = dto.getStudentNum();
            userEntity = userRepository.findByStudentNum(studentNum);
            if ( userEntity == null ) return SignInResponseDto.signInFailEmail();

            String password = dto.getPassword();
            String encodedPassword = userEntity.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if (!isMatched) return SignInResponseDto.signInFailPassword();

            token = jwtProvider.create(studentNum);

            String clientFcmToken = dto.getFcmToken();
            if(clientFcmToken != null && !clientFcmToken.isEmpty()){
                userEntity.setFcmToken(clientFcmToken);
                userRepository.save(userEntity);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(token, userEntity);
    }
}
