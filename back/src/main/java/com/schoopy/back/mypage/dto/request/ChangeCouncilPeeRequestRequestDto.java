package com.schoopy.back.mypage.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeCouncilPeeRequestRequestDto {
    private String studentNum;
    private boolean SW;
}
