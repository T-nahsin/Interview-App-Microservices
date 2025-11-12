package com.tnahsin.answerService.service;

import com.tnahsin.answerService.dto.AnswerDto;
import com.tnahsin.answerService.dto.MarksRequest;
import com.tnahsin.answerService.model.Answer;
import com.tnahsin.answerService.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
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


        answer.setQuestionId(quesId);
        answer.setQuestionText(text);
        answer.setId("Ans:"+quesId);

        boolean b = userServiceWebClient.get()
                .uri("http://QUESTION-SERVICE/question/save-ans?ans={ans}&quesId={quesId}", answer.getAnswerText(), quesId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        if(b == true) {
            answerRepository.save(answer);
            return answer.getAnswerText() + "\n" + "SAVED !!";
        }else {
            log.info("Can't connect with save answer api");
            return "Got answer but not saved due to question service";
        }
    }

    public String getAnswer(String answerId) {
        boolean check = answerRepository.findById(answerId).isEmpty();
        if(!check) return null;

        Answer answer = answerRepository.findById(answerId).get();
        return answer.getAnswerText();
    }
}
