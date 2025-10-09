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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;


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

    public String getAns(String quesId) {
        Question question = questionRepository.findById(quesId).get();
        return question.getAnswer();
    }

    public String getUserId(String quesId) {
        Question question = questionRepository.findById(quesId).get();
        String userId = question.getUserId();
        return userId;
    }

    public Boolean saveAns(String ans , String quesId) {
        Question question = questionRepository.findById(quesId).get();
        question.setAnswer(ans);
        questionRepository.save(question);
        return true;
    }

    public Map<String,String> getInterviewQuestions(String userId, String mode) {
        UserResponse userResponse = webClientBuilder.build().get()
                .uri("http://USER-SERVICE/user/fetchUser?userId={id}", userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block();
        String num = "";
        mode = mode.toLowerCase();
        if(Objects.equals(mode, "easy")) {
             num = "5";
        }else if(mode.equals("medium")) {
            num = "7";
        }else{
            num = "10";
        }
        String role = userResponse.getJob().replace(" ", "_");
        List<String> text = webClientBuilder.build().get()
                .uri("http://AI-SERVICE/gemini/generate-interview?role={role}&level={level}&num={num}", role, userResponse.getLevel(),num)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
                .block();

        Map<String,String> ques = new HashMap<>();
        int i = 0;
        for(String q : text) {
            Question question = new Question();
            question.setId("Q:"+userId+i);
            i++;
            question.setRole(userResponse.getJob());
            question.setLevel(userResponse.getLevel());
            question.setUserId(userResponse.getId());
            question.setQuestionText(q);
            questionRepository.save(question);

            ques.put(question.getId(),question.getQuestionText());
        }
        return ques;
    }
}
