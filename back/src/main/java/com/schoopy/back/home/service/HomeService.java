package com.schoopy.back.home.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.schoopy.back.home.dto.request.GetEventInformationRequestDto;
import com.schoopy.back.home.dto.response.*;

public interface HomeService {
        List<GetHomeResponseDto> home();
        ResponseEntity<? super GetEventInformationResponseDto> getEventInformation(GetEventInformationRequestDto requestBody);

}
