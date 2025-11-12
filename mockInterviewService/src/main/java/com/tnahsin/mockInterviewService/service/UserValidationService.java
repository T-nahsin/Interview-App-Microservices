package com.tnahsin.mockInterviewService.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserValidationService {

    @Autowired
    private WebClient.Builder webClientBuilder;


    public boolean validateUser(String userId) {
        log.info("Calling User Validation API for userId: {}", userId);
        try{
            return webClientBuilder.build()
                    .get()
                    .uri("http://USER-SERVICE/user/validate?userId={id}", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("User Not Found: " + userId);
        }
    }
}
