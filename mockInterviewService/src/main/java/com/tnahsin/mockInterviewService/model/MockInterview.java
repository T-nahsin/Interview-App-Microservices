package com.tnahsin.mockInterviewService.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Mock-interview")
public class MockInterview {
    @Id
    private String id;
    private String userId;
    private String mode;
    private Map<String,String> ques;
    private Map<String,String> results = new HashMap<>();
    private Map<String, String> suggestions = new HashMap<>();
    private String status;
    private String totalMarks;
}
