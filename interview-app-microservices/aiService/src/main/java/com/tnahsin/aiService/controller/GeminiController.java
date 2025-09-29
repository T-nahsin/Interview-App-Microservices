package com.tnahsin.aiService.controller;

import com.tnahsin.aiService.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;


@RestController
@RequestMapping("/gemini")
public class GeminiController {

    @Autowired
    GeminiService geminiService;

    @GetMapping("/generate")
    public ResponseEntity<String> generateQues(@RequestParam String role, @RequestParam String level) {
        String ques = geminiService.generateQuestion(role, level);

        return ResponseEntity.ok(ques);
    }
}
