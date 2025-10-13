package com.schoopy.back.event.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ApplicationRequestDto {
    private String studentNum;
    private Long eventCode;

    private List<AnswerDto> answer;

    @Data
    public static class AnswerDto {
        private Long questionId;
        private String answerText;
        private List<String> answerList;
    }
}