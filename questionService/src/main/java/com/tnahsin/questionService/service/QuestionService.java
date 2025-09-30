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
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;



    public String getQuestions(String userId) {
        UserResponse userResponse = webClientBuilder.build().get()
                .uri("http://USER-SERVICE/user/fetchUser?userId={id}", userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block();

        Question question = new Question();
        question.setRole(userResponse.getJob());
        question.setLevel(userResponse.getLevel());
        question.setUserId(userResponse.getId());


        String role = question.getRole().replace(" ", "_");


        String text = webClientBuilder.build().get()
                .uri("http://AI-SERVICE/gemini/generate?role={role}&level={level}", role, question.getLevel())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        question.setQuestionText(text);

        questionRepository.save(question);
        return text;
    }

    public Map<String, String> getQuesForUser(String userId) {
        List<Question> questions = questionRepository.findByUserId(userId);
        Map<String,String> quesText = new HashMap<>();
        for(Question ques : questions){
            quesText.put(ques.getId(), ques.getQuestionText());
        }
        return quesText;
    }

    public String getQues(String quesId) {
        Question question = questionRepository.findById(quesId).get();
        return question.getQuestionText();
    }
}
