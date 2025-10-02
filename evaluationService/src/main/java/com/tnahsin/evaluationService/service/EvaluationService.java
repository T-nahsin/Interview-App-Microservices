package com.tnahsin.evaluationService.service;

import com.tnahsin.evaluationService.dto.MarksRequest;
import com.tnahsin.evaluationService.model.Evaluations;
import com.tnahsin.evaluationService.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class EvaluationService {
    private final WebClient serviceWebClient;

    @Autowired
    EvaluationRepository evaluationRepository;

    public String evaluateAnswer(String quesId) {
        String questiontext = serviceWebClient
                .get()
                .uri("http://QUESTION-SERVICE/question/fetch-ques-by-id?quesId={quesId}", quesId)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String userId = serviceWebClient
                .get()
                .uri("http://QUESTION-SERVICE/question/fetch-user?quesId={quesId}", quesId)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String answerText = serviceWebClient
                .get()
                .uri("http://QUESTION-SERVICE/question/fetch-answer?quesId={quesId}", quesId)
                .retrieve()
                .bodyToMono(String.class)
                .block();


        MarksRequest marks = new MarksRequest();
        marks.setQuestion(questiontext);
        marks.setAnswer(answerText);

        String mark = serviceWebClient
                .post()
                .uri("http://AI-SERVICE/gemini/marks")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(marks)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Evaluations evaluations = new Evaluations();
        evaluations.setUserId(userId);
        evaluations.setQuesId(quesId);
        evaluations.setMarks(mark);

        evaluationRepository.save(evaluations);
        return "Score is "+mark+" /10";
    }

    public String suggest(String quesId) {
        Evaluations evaluations = evaluationRepository.findByQuesId(quesId);
        if(evaluations == null)
            throw new RuntimeException("No questions of this Id");

        String questiontext = serviceWebClient
                .get()
                .uri("http://QUESTION-SERVICE/question/fetch-ques-by-id?quesId={quesId}", quesId)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String answerText = serviceWebClient
                .get()
                .uri("http://QUESTION-SERVICE/question/fetch-answer?quesId={quesId}", quesId)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        MarksRequest marks = new MarksRequest();
        marks.setQuestion(questiontext);
        marks.setAnswer(answerText);

        String suggestion = serviceWebClient
                .post()
                .uri("http://AI-SERVICE/gemini/suggestion")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(marks)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        evaluations.setSuggestion(suggestion);
        evaluationRepository.save(evaluations);
        return suggestion;
    }
}
