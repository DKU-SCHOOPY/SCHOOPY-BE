package com.schoopy.back.notice.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CRequestDto {
    private long noticeId;
    private boolean accept;
}
