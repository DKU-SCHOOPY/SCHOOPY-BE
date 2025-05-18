package com.schoopy.back.event.dto.response;

import com.schoopy.back.event.common.EventResponseCode;
import com.schoopy.back.event.common.EventResponseMessage;
import com.schoopy.back.global.dto.ResponseDto;
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
public class UpdatePaymentStatusResponseDto {
    private boolean updatedStatus;

    public static ResponseEntity<UpdatePaymentStatusResponseDto> success(boolean status) {
        UpdatePaymentStatusResponseDto responseBody = new UpdatePaymentStatusResponseDto();
        responseBody.setUpdatedStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> updateFail() {
        ResponseDto responseBody = new ResponseDto(EventResponseCode.UPDATE_FAIL, EventResponseMessage.UPDATE_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}