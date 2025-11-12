package com.tnahsin.resumeService.controller;


import com.tnahsin.resumeService.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    @Autowired
    ResumeService resumeService;

    @PostMapping("/check")
    public String health(){
        return "health";
    }

    @PostMapping("/evaluate")
    public ResponseEntity<String> submit(@RequestParam MultipartFile file,
                                         @RequestParam String userId){
        if(file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        String text = resumeService.pdfToText(file);
        String evaluation = resumeService.getEvaluation(userId, text);
        return ResponseEntity.ok(evaluation);
    }
}
