package com.example.userservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private long userId;
    private int age;
    private LocalDateTime userCreatedAt;
    private String status;
    private String country;
    private String userName;
    private String gender;
}
