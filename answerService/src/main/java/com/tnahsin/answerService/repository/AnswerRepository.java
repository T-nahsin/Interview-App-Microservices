package com.tnahsin.answerService.repository;

import com.tnahsin.answerService.model.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerRepository extends MongoRepository<Answer, String> {

}
