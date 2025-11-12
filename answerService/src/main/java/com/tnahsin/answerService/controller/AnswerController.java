package com.tnahsin.answerService.controller;


import com.tnahsin.answerService.dto.AnswerDto;
import com.tnahsin.answerService.service.AnswerService;
import com.tnahsin.answerService.service.UserValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @Autowired
    UserValidationService userValidationService;


    @Operation(summary = "Write answers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "answer written successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/write-answer")
    public ResponseEntity<String> answerGet(@RequestBody AnswerDto answer){
        if(userValidationService.validateUser(answer.getUserId())) {
            return ResponseEntity.ok(answerService.getAnswer(answer.getUserId(), answer.getQuesId(), answer));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Fetches answers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "answers fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/fetch-answer")
    public ResponseEntity<String> fetchAnswer(@RequestParam String answerId){
        return ResponseEntity.ok(answerService.getAnswer(answerId));
    }
}
