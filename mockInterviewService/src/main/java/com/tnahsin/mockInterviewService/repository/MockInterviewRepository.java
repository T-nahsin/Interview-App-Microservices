package com.tnahsin.mockInterviewService.repository;

import com.tnahsin.mockInterviewService.model.MockInterview;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MockInterviewRepository extends MongoRepository<MockInterview,String> {
}
