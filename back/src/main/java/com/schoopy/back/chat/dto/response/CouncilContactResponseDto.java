package com.schoopy.back.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouncilContactResponseDto {
    private String department;
    private String presidentStudentNum;
    private String presidentName;
    private Long roomId;
}
