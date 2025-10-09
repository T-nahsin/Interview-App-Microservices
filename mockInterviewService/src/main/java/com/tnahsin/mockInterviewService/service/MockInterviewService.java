package com.tnahsin.mockInterviewService.service;


import com.tnahsin.mockInterviewService.dto.AnswerDto;
import com.tnahsin.mockInterviewService.dto.AnswerRequest;
import com.tnahsin.mockInterviewService.dto.UserResponse;
import com.tnahsin.mockInterviewService.model.MockInterview;
import com.tnahsin.mockInterviewService.repository.MockInterviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MockInterviewService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
        private MockInterviewRepository mockInterviewRepository;

    public Map<String,String> generateInterview(String userId,String mode) {
        MockInterview mockInterview = new MockInterview();
        mockInterview.setId("mock:"+"level:"+userId);
        mockInterview.setUserId(userId);

        UserResponse user = webClientBuilder.build().get()
                .uri("http://USER-SERVICE/user/fetchUser?userId={id}", userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block();

        Map<String,String> ques = webClientBuilder.build().get()
                .uri("http://QUESTION-SERVICE/question/generate-ques?userId={id}&mode={mode}", userId,mode)
                .retrieve()
                .bodyToMono(new org.springframework.core.ParameterizedTypeReference<Map<String, String>>() {})
                .block();

        mockInterview.setQues(ques);
        boolean saved = webClientBuilder.build()
                .get()
                .uri("http://USER-SERVICE/user/save-mock?userId={id}&mockId={mockId}", userId, mockInterview.getId())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        mockInterview.setStatus("in_progress");
        mockInterview.setMode(mode);
        if(saved) {
            mockInterviewRepository.save(mockInterview);
            return ques;
        }else{
            return null;
        }
    }

    public String writeAns(String quesId, AnswerRequest answerRequest) {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setQuesId(quesId);
        String userId = webClientBuilder.build().get()
                .uri("http://QUESTION-SERVICE/question/fetch-user?quesId={id}", quesId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
            answerDto.setUserId(userId);
        answerDto.setAnswer(answerRequest.getAnswer());
        String answer = webClientBuilder.build().post()
                .uri("http://ANSWER-SERVICE/answer/write-answer")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(answerDto)
                .retrieve()
                .bodyToMono(String.class)
                .block();


        return answer;
    }

    public String evaluateInterview(String mockId) {
        MockInterview mockInterview;
        if(mockInterviewRepository.findById(mockId).isPresent())
            mockInterview = mockInterviewRepository.findById(mockId).get();
        else throw new RuntimeException("Mock Interview cant be found");

        mockInterview.setStatus("completed");
        String mode = mockInterview.getMode().toLowerCase();

        int sum = 0;
        Map<String ,String> map = mockInterview.getQues();

        for (Map.Entry<String, String> entry : map.entrySet()){
            String quesId = entry.getKey();
            log.info("Calling the evaluation service to get marks");
            String marks = webClientBuilder.build().get()
                    .uri("http://EVALUATION-SERVICE/evaluate/get-marks?quesId={quesId}", quesId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            String mark="";
            for(int i = 0 ; i < marks.length();i++){
                char ch = marks.charAt(i);
                if(ch == '/'){
                    break;
                }
                mark +=  ch;
            }

            sum = sum + Integer.parseInt(mark);

            mockInterview.getResults().put(quesId,mark);
        }
        mockInterview.setTotalMarks(Integer.toString(sum));
        mockInterviewRepository.save(mockInterview);

        log.info("Giving output");
        if(mode.equals("easy")){
            return "Your score: "+ (sum);
        }else if(mode.equals("medium")){
            return  "Your score: " + (sum);
        }else{
            return  "Your score: " + (sum);
        }
    }

    public String getSuggestion(String mockId) {
        MockInterview mockInterview;
        if(mockInterviewRepository.findById(mockId).isPresent())
            mockInterview = mockInterviewRepository.findById(mockId).get();
        else throw new RuntimeException("Mock Interview cant be found");

        Map<String ,String> map = mockInterview.getQues();
        for (Map.Entry<String, String> entry : map.entrySet()){
            String quesId = entry.getKey();
            log.info("Getting Suggestions for Mock-Interview");
            String suggestion = webClientBuilder.build().get()
                    .uri("http://EVALUATION-SERVICE/evaluate/get-suggestion?quesId={quesId}", quesId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            mockInterview.getSuggestions().put(quesId,suggestion);
        }
        mockInterviewRepository.save(mockInterview);
        String finalSuggestion ="";
        for (Map.Entry<String, String> entry : mockInterview.getSuggestions().entrySet()){
            finalSuggestion += entry.getValue()+" \n";
        }
        return finalSuggestion;
    }
}
