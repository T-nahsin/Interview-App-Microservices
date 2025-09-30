package com.tnahsin.answerService.service;

import com.tnahsin.answerService.dto.AnswerDto;
import com.tnahsin.answerService.dto.MarksRequest;
import com.tnahsin.answerService.model.Answer;
import com.tnahsin.answerService.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class AnswerService {


    private final WebClient userServiceWebClient;

    @Autowired
    AnswerRepository answerRepository;

    public String getAnswer(String userId, String quesId, AnswerDto answerDto) {
        String ques = quesId.toString();
        Answer answer = new Answer();
        answer.setUserId(userId);
        answer.setQuestionId(ques);
        answer.setAnswerText(answerDto.getAnswer());


        String text = userServiceWebClient
                .get()
                .uri("http://QUESTION-SERVICE/question/fetch-ques-by-id?quesId={ques}", ques)
                .retrieve()
                .bodyToMono(String.class)
                .block();


        answer.setQuestionText(text);

        answerRepository.save(answer);
        return answer.getAnswerText()+"/n"+"SAVED !!";
    }

    public String getScores(String answerId) {
        if(answerRepository.findById(answerId).isEmpty()){
            return null;
        }
        Answer answer = answerRepository.findById(answerId).get();
        MarksRequest marks = new MarksRequest();
        marks.setQuestion(answer.getQuestionText());
        marks.setAnswer(answer.getAnswerText());

        String mark = userServiceWebClient
                .post()
                .uri("http://AI-SERVICE/gemini/marks")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(marks)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return mark;
    }
}
