package com.example.randompairchat.exception;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ErrorResponse {
    private int errorCode;
    private String errorMessage;
    private LocalDateTime errorOccuredAt;
    private String path;                  // /api/chat/send
    private String method;                // POST
    private String exception;
}

