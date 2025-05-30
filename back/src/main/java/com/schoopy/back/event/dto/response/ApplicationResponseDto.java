package com.schoopy.back.event.dto.response;

import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.event.common.EventResponseCode;
import com.schoopy.back.event.common.EventResponseMessage;
import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponseDto {

    private Long applicationId;
    private EventEntity eventCode;
    private UserEntity studentNum;
    private Boolean isStudent;
    private Boolean councilFeePaid;
    private Boolean isPaymentCompleted;

    public static ResponseEntity<ApplicationResponseDto> success(Long applicationId, EventEntity eventCode
            , UserEntity studentNum, Boolean isStudent, Boolean councilFeePaid, Boolean isPaymentCompleted) {
        ApplicationResponseDto responseBody = new ApplicationResponseDto();
        responseBody.setApplicationId(applicationId);
        responseBody.setEventCode(eventCode);
        responseBody.setStudentNum(studentNum);
        responseBody.setIsStudent(isStudent);
        responseBody.setCouncilFeePaid(councilFeePaid);
        responseBody.setIsPaymentCompleted(isPaymentCompleted);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> submitFail() {
        ResponseDto responseBody = new ResponseDto(EventResponseCode.SUBMIT_FAIL, EventResponseMessage.SUBMIT_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}