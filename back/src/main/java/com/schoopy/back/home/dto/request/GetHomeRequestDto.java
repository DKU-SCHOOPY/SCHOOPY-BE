package com.schoopy.back.home.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetHomeRequestDto {
    private String studentNum;
    private boolean president;
}
