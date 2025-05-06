package com.schoopy.back.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FcmMessageDto {
    private String targetToken;
    private String title;
    private String body;
}
