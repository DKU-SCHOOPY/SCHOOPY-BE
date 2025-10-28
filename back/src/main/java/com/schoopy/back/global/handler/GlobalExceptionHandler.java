package com.schoopy.back.global.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.schoopy.back.global.dto.ResponseDto;
import com.schoopy.back.global.common.ResponseCode;
import com.schoopy.back.global.common.ResponseMessage;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {

        // 가장 첫 번째 에러 메시지만 반환
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse(ResponseMessage.INCORRECT_REQUEST);

        ResponseDto responseBody = new ResponseDto(
                ResponseCode.INCORRECT_REQUEST,
                errorMessage
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
