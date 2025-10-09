package com.tnahsin.questionService.controller;


import com.tnahsin.questionService.service.QuestionService;
import com.tnahsin.questionService.service.UserValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "Generates questions for Mock Interview using AI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question generated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/generate-ques")
    public ResponseEntity<Map<String,String>> interview(@RequestParam String userId,
                                            @RequestParam String  mode) {
        Map<String,String> map=questionService.getInterviewQuestions(userId,mode);
        return ResponseEntity.ok(map);
    }

    @Operation(summary = "Generates questions for using AI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question generated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/generate-for")
    public ResponseEntity<String> generateQues(@RequestParam String userId) {
        if(userValidationService.validateUser(userId))
            return ResponseEntity.ok(questionService.getQuestions(userId));
        return new ResponseEntity<>(HttpStatusCode.valueOf(502));
    }

    @Operation(summary = "Fetches Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/fetch-user")
    public ResponseEntity<String> fetchUsers(@RequestParam String quesId) {
        return ResponseEntity.ok(questionService.getUserId(quesId));
    }

    @GetMapping("/save-ans")
    public Boolean saveAns(@RequestParam String ans,@RequestParam String quesId){
        return questionService.saveAns(ans, quesId);
    }

    @GetMapping("/fetch-ques")
    public ResponseEntity<Map<String, String>> fetchQues(@RequestParam String userId) {
        Map<String, String> questions = questionService.getQuesForUser(userId);
        if(questions.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return ResponseEntity.ok(questions);
    }

    @Operation(summary = "fetches questions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/fetch-ques-by-id")
    public String getQuestion(@RequestParam String quesId){
        return questionService.getQues(quesId);
    }


    @Operation(summary = "Generates answers for Mock Interview")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question generated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/fetch-answer")
    public String getAns(@RequestParam String quesId){
        return questionService.getAns(quesId);
    }
}