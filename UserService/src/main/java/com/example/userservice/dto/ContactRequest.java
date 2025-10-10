package com.example.userservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactRequest {
    @NotNull(message = "UserId is required")
    private long userId;
    @NotNull(message = "Message is required")
    @Size(min = 20, message = "Message is too short")
    private String message;
}
