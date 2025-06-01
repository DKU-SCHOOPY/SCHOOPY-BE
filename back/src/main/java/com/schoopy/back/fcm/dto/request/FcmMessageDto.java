package com.schoopy.back.fcm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FcmMessageDto {
    private String targetToken;
    private String title;
    private String body;
    private String receiver;
}
