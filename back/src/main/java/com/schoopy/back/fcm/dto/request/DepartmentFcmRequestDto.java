package com.schoopy.back.fcm.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentFcmRequestDto {
    private String department;
    private String title;
    private String body;
}