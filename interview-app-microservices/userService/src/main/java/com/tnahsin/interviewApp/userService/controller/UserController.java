package com.tnahsin.interviewApp.userService.controller;


import com.tnahsin.interviewApp.userService.dtos.UserRequest;
import com.tnahsin.interviewApp.userService.dtos.UserResponse;
import com.tnahsin.interviewApp.userService.model.User;
import com.tnahsin.interviewApp.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.registerUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/fetchUser")
    public ResponseEntity<UserResponse> getUserProfile(@RequestParam String userId){
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }
    @GetMapping("/validate")
    public boolean checkValidity(@RequestParam String userId) {
        return userService.isUserValid(userId);
    }
}
