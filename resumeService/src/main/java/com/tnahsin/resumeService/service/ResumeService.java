package com.tnahsin.resumeService.service;

import com.tnahsin.resumeService.dtos.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class ResumeService {

    @Autowired
    private WebClient.Builder webClientBuilder;
    
    public String pdfToText(MultipartFile file) {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract text from PDF: " + e.getMessage());
        }
    }

    public String getEvaluation(String userId, String text) {
        UserResponse userResponse = webClientBuilder.build().get()
                .uri("http://USER-SERVICE/user/fetchUser?userId={id}", userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block();

        if(userResponse == null){
            throw new RuntimeException("UserResponse is null!");
        }
        log.info("Calling gemini service!!");
        String role = userResponse.getJob();
        return webClientBuilder.build()
                .post()
                .uri("http://AI-SERVICE/gemini/resume?role={role}", role)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(text)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
