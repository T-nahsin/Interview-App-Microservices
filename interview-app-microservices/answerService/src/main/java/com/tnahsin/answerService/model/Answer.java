package com.tnahsin.answerService.model;


import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Answer {

    @Id
    private String id;
    private String userId;
    private String questionId;
    private String questionText;
    private String answerText;
}
