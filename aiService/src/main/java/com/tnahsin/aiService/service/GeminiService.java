package com.tnahsin.aiService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
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

    public String getScores(String question, String answer) {
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[]{
                                Map.of("text","Hi! only give me the rating out of 10 of this answer"+ answer+" for this question"+question+" ,only give rating from 1 to 10 dont write anything else!!")
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

    public String getSuggestion(String question, String answer) {
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[]{
                                Map.of("text","Hi! only give me the suggestion for this answer"+ answer+" for this question"+question+" ,only give suggestion as an interviewer in 20 - 30 words!!")
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

    public List<String> generateInterviewQuestions(String role, String level, String mode) {
        String prompt = "Generate "+mode+ "interview questions for a " + role + " position of "+level+"level. "
                + "Return only a JSON array of questions, no extra text.";


        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );
        Map<String, Object> response = webClient.post()
                .uri(geminiApiUrl + geminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();


        List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
        Map<String, Object> firstCandidate = candidates.get(0);
        Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");
        List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
        String text = (String) parts.get(0).get("text");

        ObjectMapper mapper = new ObjectMapper();
        try {
            String cleaned = text.replaceAll("```json", "")
                    .replaceAll("```", "")
                    .trim();

            return mapper.readValue(cleaned, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Gemini response: " + text, e);
        }
    }
}
