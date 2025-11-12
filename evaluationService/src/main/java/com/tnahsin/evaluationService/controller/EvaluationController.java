package com.tnahsin.evaluationService.controller;


import com.tnahsin.evaluationService.service.EvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/evaluate")
public class EvaluationController {

    @Autowired
    EvaluationService evaluationService;


    @Operation(summary = "Get marks for questions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "marks got successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/get-marks")
    public ResponseEntity<String> getScore(@RequestParam String quesId){
        return ResponseEntity.ok(evaluationService.evaluateAnswer(quesId));
    }

    @Operation(summary = "Get suggestion for questions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "suggestion provided successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/get-suggestion")
    public ResponseEntity<String> getSuggestion(@RequestParam String quesId){
        return ResponseEntity.ok(evaluationService.suggest(quesId));
    }
}