package com.tnahsin.aiService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class  GeminiService {

    private final WebClient webClient;


    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public String generateQuestion(String role, String level) {


        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[]{
                                Map.of("text", "Hi, I am preparing for "+role+" and i want "+level
                                        +" level only 1 question for interview, only provide me question nothing else")
                        })
                }
        );


        String response =  webClient.post()
                .uri(geminiApiUrl + geminiApiKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError, resp -> resp.bodyToMono(String.class)
                        .flatMap(error -> {
                            System.err.println("Gemini error response: " + error);
                            return Mono.error(new RuntimeException("Gemini API error: " + error));
                        }))
                .bodyToMono(String.class)
                .block();


        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            String text = root.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

            return text;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
