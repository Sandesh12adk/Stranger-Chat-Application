package com.example.userservice.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContactResponse {
    private long id;
    private String msg;
    private LocalDateTime sentAt;
    private long userId;
}
