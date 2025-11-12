package com.tnahsin.aiService.controller;

import com.tnahsin.aiService.dto.MarksRequest;
import com.tnahsin.aiService.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/gemini")
public class GeminiController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    GeminiService geminiService;

    @GetMapping("/generate-interview")
    public ResponseEntity<List<String>> generateInterview(@RequestParam String role,
                                                    @RequestParam String level,
                                                    @RequestParam String num) {
        List<String> ques = geminiService.generateInterviewQuestions(role, level,num);

        return ResponseEntity.ok(ques);
    }

    @GetMapping("/generate")
    public ResponseEntity<String> generateQues(@RequestParam String role,
                                               @RequestParam String level) {
        String ques = geminiService.generateQuestion(role, level);

        return ResponseEntity.ok(ques);
    }

    @PostMapping("/marks")
    public ResponseEntity<String> getScore(@RequestBody MarksRequest request) {
        return ResponseEntity.ok(geminiService.getScores(request.getQuestion(),request.getAnswer()));
    }


    @PostMapping("/suggestion")
    public ResponseEntity<String> suggestion(@RequestBody MarksRequest request) {
        return ResponseEntity.ok(geminiService.getSuggestion(request.getQuestion(),request.getAnswer()));
    }

    @PostMapping("/resume")
    ResponseEntity<String> resumeEvaluate(@RequestParam String role,
                                          @RequestBody String text){
        String result = geminiService.evaluateResume(text,role);
        return ResponseEntity.ok(result);
    }
}
