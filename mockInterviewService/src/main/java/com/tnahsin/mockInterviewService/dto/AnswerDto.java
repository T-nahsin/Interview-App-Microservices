package com.tnahsin.mockInterviewService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {
    private String userId;
    private String quesId;
    private String answer;
}
