package com.tnahsin.interviewApp.userService.controller;


import com.tnahsin.interviewApp.userService.dtos.UserRequest;
import com.tnahsin.interviewApp.userService.dtos.UserResponse;
import com.tnahsin.interviewApp.userService.model.User;
import com.tnahsin.interviewApp.userService.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "Registers users in the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.registerUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "Fetches the users from Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/fetchUser")
    public ResponseEntity<UserResponse> getUserProfile(@RequestParam String userId){
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }
    @Operation(summary = "save mock Interview manually")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/save-mock")
    public boolean saveMockInterview(@RequestParam String userId,
                                     @RequestParam String mockId){
        return userService.saveMock(userId,mockId);
    }

    @Operation(summary = "Fetches the users  validity from Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/validate")
    public boolean checkValidity(@RequestParam String userId) {
        return userService.isUserValid(userId);
    }
}
