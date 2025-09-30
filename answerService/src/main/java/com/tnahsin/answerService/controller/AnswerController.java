package com.tnahsin.answerService.controller;


import com.tnahsin.answerService.dto.AnswerDto;
import com.tnahsin.answerService.model.Answer;
import com.tnahsin.answerService.service.AnswerService;
import com.tnahsin.answerService.service.UserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @Autowired
    UserValidationService userValidationService;

    @PostMapping("/write-answer")
    public ResponseEntity<String> answerGet(@RequestBody AnswerDto answer){
        if(userValidationService.validateUser(answer.getUserId())) {
            return ResponseEntity.ok(answerService.getAnswer(answer.getUserId(), answer.getQuesId(), answer));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get-marks")
    public ResponseEntity<String>getScore(@RequestParam String answerId){
        return ResponseEntity.ok(answerService.getScores(answerId));
    }
}
