package com.tnahsin.questionService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tnahsin.questionService.model.Question;
import com.tnahsin.questionService.model.UserResponse;
import com.tnahsin.questionService.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;



    public String getQuestions(String userId){
        UserResponse userResponse= webClientBuilder.build().get()
                .uri("http://USER-SERVICE/user/fetchUser?userId={id}", userId)
                .retrieve()
                .bodyToMono(UserResponse.class)  // uses your DTO
                .block();

        Question question = new Question();
        question.setRole(userResponse.getJob());
        question.setLevel(userResponse.getLevel());
        question.setUserId(userResponse.getId());



        question.setQuestionText(text);

        questionRepository.save(question);



    }

    public Map<String, String> getQuesForUser(String userId) {
        List<Question> questions = questionRepository.findByUserId(userId);
        Map<String,String> quesText = new HashMap<>();
        for(Question ques : questions){
            quesText.put(ques.getId(), ques.getQuestionText());
        }
        return quesText;
    }
}
