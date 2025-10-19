package com.schoopy.back.event.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationStatusResponseDto {
    public enum ApplicationStatus {
        PENDING, // 승인됨
        APPROVED, // 대기
        NONE // 반려 혹은 조회 실패
    }

    private boolean exists;
    private Boolean approved;
    private ApplicationStatus status;

    public static ApplicationStatusResponseDto approved() {
        return ApplicationStatusResponseDto.builder()
                .exists(true).approved(true).status(ApplicationStatus.APPROVED).build();
    }
    public static ApplicationStatusResponseDto pending() {
        return ApplicationStatusResponseDto.builder()
                .exists(true).approved(false).status(ApplicationStatus.PENDING).build();
    }
    public static ApplicationStatusResponseDto none() {
        return ApplicationStatusResponseDto.builder()
                .exists(false).approved(null).status(ApplicationStatus.NONE).build();
    }    
}