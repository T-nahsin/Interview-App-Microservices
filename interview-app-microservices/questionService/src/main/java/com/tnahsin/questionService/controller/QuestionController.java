package com.tnahsin.questionService.controller;


import com.tnahsin.questionService.service.QuestionService;
import com.tnahsin.questionService.service.UserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    UserValidationService userValidationService;

    @PostMapping("/generate-for")
    public ResponseEntity<String> generateQues(@RequestParam String userId) {
        if(userValidationService.validateUser(userId))
            return ResponseEntity.ok(questionService.getQuestions(userId));
        return new ResponseEntity<>(HttpStatusCode.valueOf(502));
    }

    @GetMapping("/fetch-ques")
    public ResponseEntity<Map<String, String>> fetchQues(@RequestParam String userId) {
        Map<String, String> questions = questionService.getQuesForUser(userId);
        if(questions.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return ResponseEntity.ok(questions);
    }
}
