package com.tnahsin.mockInterviewService.controller;


import com.tnahsin.mockInterviewService.dto.AnswerRequest;
import com.tnahsin.mockInterviewService.service.MockInterviewService;
import com.tnahsin.mockInterviewService.service.UserValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/mock")
public class MockInterviewController {

    @Autowired
    UserValidationService userValidationService;

    @Autowired
    MockInterviewService mockInterviewService;

    @Operation(summary = "Create mock Interview for users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interview created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/create-mock")
    public ResponseEntity<Map<String,String>> mock(@RequestParam String userId,
                                                   @RequestParam String mode) {
        if(userValidationService.validateUser(userId)){
            Map<String, String>ques = mockInterviewService.generateInterview(userId,mode);
            return ResponseEntity.ok(ques);
        }
        throw new RuntimeException("User can't be validated");
    }

    @Operation(summary = "Write answers for Interviews for users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Answer submitted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/mock-answer")
    public ResponseEntity<String> mock(@RequestParam String quesId,
                                       @RequestBody AnswerRequest answerRequest) {

        String ans = mockInterviewService.writeAns(quesId, answerRequest);
        return ResponseEntity.ok(ans);
    }

    @Operation(summary = "Submit the mock Interview for users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interview saved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/submit")
    public ResponseEntity<String> submit(@RequestParam String mockId) {
        String score = mockInterviewService.evaluateInterview(mockId);
        return ResponseEntity.ok(score);
    }

    @Operation(summary = "Get suggestions for Interview for users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suggestion provided successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/suggestions")
    public ResponseEntity<String> suggest(@RequestParam String mockId) {
        String score = mockInterviewService.getSuggestion(mockId);
        return ResponseEntity.ok(score);
    }
}
