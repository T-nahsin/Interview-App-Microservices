package com.tnahsin.interviewApp.userService.service;


import com.tnahsin.interviewApp.userService.dtos.UserRequest;
import com.tnahsin.interviewApp.userService.dtos.UserResponse;
import com.tnahsin.interviewApp.userService.model.User;
import com.tnahsin.interviewApp.userService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserResponse registerUser(UserRequest userRequest) {
        User user = new User();
        user.setId(userRequest.getId());
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setJob(userRequest.getJob());
        user.setLevel(userRequest.getLevel());

        userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setJob(user.getJob());
        userResponse.setLevel(user.getLevel());
        return userResponse;
    }

     public UserResponse getUserProfile(String userId) {
         User user = userRepository.findById(userId)
                 .orElseThrow(() -> new RuntimeException("User Not Found"));

         UserResponse userResponse = new UserResponse();
         userResponse.setId(user.getId());
         userResponse.setUsername(user.getUsername());
         userResponse.setEmail(user.getEmail());
         userResponse.setJob(user.getJob());
         userResponse.setLevel(user.getLevel());
         return userResponse;
     }

    public boolean isUserValid(String userId) {
        return userRepository.existsById(userId);
    }

    public boolean saveMock(String userId, String mockId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        List<String> mocks = new ArrayList<>();
        if(user.getPastInterviewIds()!= null) mocks.addAll(user.getPastInterviewIds());
        mocks.add(mockId);
        user.setPastInterviewIds(mocks);
        userRepository.save(user);
        return true;
    }
}
