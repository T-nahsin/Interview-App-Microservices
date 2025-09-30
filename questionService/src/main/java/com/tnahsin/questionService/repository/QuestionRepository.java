package com.tnahsin.questionService.repository;

import com.tnahsin.questionService.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question,String> {
    List<Question> findByUserId(String userId);
}
