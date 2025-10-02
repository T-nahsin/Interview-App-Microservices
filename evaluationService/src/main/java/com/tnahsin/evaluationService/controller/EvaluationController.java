package com.tnahsin.evaluationService.controller;


import com.tnahsin.evaluationService.service.EvaluationService;
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


    @GetMapping("/get-marks")
    public ResponseEntity<String> getScore(@RequestParam String quesId){
        return ResponseEntity.ok(evaluationService.evaluateAnswer(quesId));
    }

    @GetMapping("/get-suggestion")
    public ResponseEntity<String> getSuggestion(@RequestParam String quesId){
        return ResponseEntity.ok(evaluationService.suggest(quesId));
    }
}