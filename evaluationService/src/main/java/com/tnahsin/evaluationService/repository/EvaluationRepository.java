package com.tnahsin.evaluationService.repository;

import com.tnahsin.evaluationService.model.Evaluations;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EvaluationRepository extends MongoRepository<Evaluations,String> {

    Evaluations findByQuesId(String quesId);
}
