package com.tnahsin.interviewApp.userService.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequest {
    private String id;
    private String username;
    private String password;
    private String email;
    private String job;
    private String level;
}
