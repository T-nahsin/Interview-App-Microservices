package com.tnahsin.interviewApp.userService.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "User")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private String job;
    private String level;

    private List<String> pastInterviewIds;
}