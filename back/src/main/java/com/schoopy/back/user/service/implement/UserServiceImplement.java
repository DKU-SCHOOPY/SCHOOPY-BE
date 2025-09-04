package com.schoopy.back.user.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.schoopy.back.global.dto.OAuthHelplerResponseDto;
import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.global.helper.KakaoOauthHelper;
import com.schoopy.back.global.helper.NaverOauthHelper;
import com.schoopy.back.global.provider.EmailProvider;
import com.schoopy.back.global.provider.JwtProvider;
import com.schoopy.back.notice.repository.NoticeRepository;
import com.schoopy.back.user.dto.request.*;
import com.schoopy.back.user.dto.response.*;
import com.schoopy.back.user.entity.CertificationEntity;
import com.schoopy.back.user.entity.PresidentEntity;
import com.schoopy.back.user.entity.UserEntity;
import com.schoopy.back.user.repository.CertificationRepository;
import com.schoopy.back.user.repository.PresidentRepository;
import com.schoopy.back.user.repository.UserRepository;
import com.schoopy.back.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService{
    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;
    private final NoticeRepository noticeRepository;
    private final PresidentRepository presidentRepository; 

    private final JwtProvider jwtProvider;
    private final EmailProvider emailProvider;

    private final KakaoOauthHelper kakaoOauthHelper;
    private final NaverOauthHelper naverOauthHelper;

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

            token = jwtProvider.create(studentNum, userEntity.getRole());

            int noticeCount = noticeRepository.countByReceiverAndReadCheck(userEntity, false);
            userEntity.setNoticeCount(noticeCount);

            userRepository.save(userEntity);


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(token, userEntity);
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> naverLogin(String code, String state) {

        UserEntity user;
        String token;

        try {
            // 1. access token 요청
            String accessToken = naverOauthHelper.getAccessToken(code, state, "https://www.schoopy.co.kr/oauth2/authorization/naver");
            
            // 2. 사용자 정보 요청
            String naverId = naverOauthHelper.getUserIdFromToken(accessToken);

            // 3. 사용자 찾기
            user = userRepository.findByNaverId(naverId);
            if (user == null) return SignInResponseDto.signInFailEmail();

            // 4. JWT 생성 및 반환
            token = jwtProvider.create(user.getStudentNum(), user.getRole());
            user.setNoticeCount(noticeRepository.countByReceiverAndReadCheck(user, false));
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(token, user);
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> kakaoLogin(String code) {

        UserEntity user;
        String token;

        try {
            String accessToken = kakaoOauthHelper.getAccessToken(code, "https://www.schoopy.co.kr/oauth2/authorization/kakao");
            String kakaoId = kakaoOauthHelper.getUserIdFromToken(accessToken);

            user = userRepository.findByKakaoId(kakaoId);
            if (user == null) return SignInResponseDto.signInFailEmail();

            token = jwtProvider.create(user.getStudentNum(), user.getRole());
            user.setNoticeCount(noticeRepository.countByReceiverAndReadCheck(user, false));
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(token, user);

    }

    @Override
    public ResponseEntity<? super LinkSocialResponseDto> kakaoLink(LinkKakaoRequestDto dto) {
        try {
            String studentNum = dto.getStudentNum();
            String code = dto.getCode();

            if(studentNum == null || studentNum.isEmpty() || code == null || code.isEmpty()) {
                return ResponseDto.badRequest();
            }   

            String kakaoId = kakaoOauthHelper.getKakaoUserId(code, "https://www.schoopy.co.kr/oauth2/authorization/kakao/link");
            if (kakaoId == null || kakaoId.isEmpty()) return OAuthHelplerResponseDto.oAuthError();
            UserEntity user = userRepository.findByStudentNum(studentNum);
            if (user == null) return ResponseDto.databaseError();

            user.setKakaoId(kakaoId);
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return LinkSocialResponseDto.kakaoLinkSuccess();

    }

    @Override
    public ResponseEntity<? super LinkSocialResponseDto> naverLink(LinkNaverRequestDto dto) {
        try {
            String studentNum = dto.getStudentNum();
            String code = dto.getCode();
            String state = dto.getState();

            if(studentNum == null || studentNum.isEmpty() || code == null || code.isEmpty() || state == null || state.isEmpty()) {
                return ResponseDto.badRequest();
            }

            String naverId = naverOauthHelper.getNaverUserId(code, state, "https://www.schoopy.co.kr/oauth2/authorization/naver/link");
            if (naverId == null || naverId.isEmpty()) return OAuthHelplerResponseDto.oAuthError();
            UserEntity user = userRepository.findByStudentNum(studentNum);
            if (user == null) return ResponseDto.databaseError();

            user.setNaverId(naverId);
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return LinkSocialResponseDto.naverLinkSuccess();
    }   

    @Override
    public ResponseEntity<? super ElectResponseDto> elect(ElectRequestDto dto) {
        try {
            String department = dto.getDepartment();
            String studentNum = dto.getStudentNum();

            PresidentEntity president = presidentRepository.findByDepartment(department);
            president.setStudentNum(studentNum);

            presidentRepository.save(president);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ElectResponseDto.success();
    }
}
