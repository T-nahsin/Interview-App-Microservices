package com.tnahsin.evaluationService.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "evaluations")
public class Evaluations {

    @Id
    private String id;
    private String userId;
    private String quesId;
    private String marks;
    private String suggestion;


    public void generateEvaluationsId() {
        if (this.id == null || this.id.isBlank()) {
            this.id = "E-" + UUID.randomUUID();
        }
    }
}
