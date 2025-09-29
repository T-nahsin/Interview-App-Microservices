package com.tnahsin.interviewApp.userService.repository;

import com.tnahsin.interviewApp.userService.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
