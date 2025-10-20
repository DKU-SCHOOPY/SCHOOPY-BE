package com.schoopy.back.home.dto.response;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoopy.back.global.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetHomeListResponseDto extends ResponseDto {

    private int noticeCount;
    private List<GetHomeResponseDto> events;

    public static ResponseEntity<GetHomeListResponseDto> success(int noticeCount, List<GetHomeResponseDto> events) {
        GetHomeListResponseDto responseBody = new GetHomeListResponseDto(noticeCount, events);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
