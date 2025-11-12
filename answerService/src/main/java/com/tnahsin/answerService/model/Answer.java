package com.tnahsin.answerService.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "Answer")

public class Answer {

    @Id
    private String id;
    private String userId;
    private String questionId;
    private String questionText;
    private String answerText;

    public void generateAnswerId() {
        if (this.id == null || this.id.isBlank()) {
            this.id = "A-" + UUID.randomUUID();
        }
    }
}
