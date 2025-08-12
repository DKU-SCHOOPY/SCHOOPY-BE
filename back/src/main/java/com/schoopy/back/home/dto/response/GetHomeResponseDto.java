package com.schoopy.back.home.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetHomeResponseDto {
    private Long eventCode;
    private String eventName;
    private String department;
    private String eventDescription;
    private List<String> eventImages;
}