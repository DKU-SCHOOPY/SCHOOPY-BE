package com.schoopy.back.event.dto.response;

import com.schoopy.back.event.common.EventResponseCode;
import com.schoopy.back.event.common.EventResponseMessage;
import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.global.dto.ResponseDto;
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
public class RedirectResponseDto {
    private String url;

    public static ResponseEntity<RedirectResponseDto> success(String url){
        RedirectResponseDto responseBody = new RedirectResponseDto();
        responseBody.setUrl(url);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> redirectFail(){
        ResponseDto responseBody = new ResponseDto(EventResponseCode.REDIRECT_FAIL, EventResponseMessage.REDIRECT_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
