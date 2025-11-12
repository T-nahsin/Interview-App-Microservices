package com.tnahsin.questionService.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Question")
public class Question {

    @Id
    private String id;
    private String userId;
    private String role;
    private String level;
    private String questionText;
    private String answer;

    public void generateQuestionId() {
        if (this.id == null || this.id.isBlank()) {
            this.id = "Q-" + UUID.randomUUID();
        }
    }
}
