package com.schoopy.back.event.dto.response;

import lombok.*;
import java.util.List;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class ExportExcelDataResponseDto {
    private Long eventCode;
    private String eventName;

    // 기본 헤더(고정)
    @Builder.Default
    private List<String> baseHeaders = List.of(
        "studentNum", "name", "department", "birthDay", "gender", "phoneNum", "councilPee"
    );

    // 질문 목록(순서 보장: questionId 오름차순)
    private List<ExportQuestionDto> questions;

    // 각 신청자의 한 행(row)
    private List<ExportSubmissionRowDto> rows;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ExportQuestionDto {
        private Long questionId;
        private String questionText;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ExportSubmissionRowDto {
        // base headers
        private String studentNum;
        private String name;
        private String department;
        private String birthDay;
        private String gender;
        private String phoneNum;
        private boolean councilPee;

        // 질문 순서에 맞춘 답변(복수선택은 ", "로 join)
        private List<String> answers;
    }
}
