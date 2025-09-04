package com.schoopy.back.notice.dto.request;

import lombok.Getter;

@Getter
public class ReadAllNoticeRequestDto {
    private String studentNum;
    private boolean isPresident;
}
