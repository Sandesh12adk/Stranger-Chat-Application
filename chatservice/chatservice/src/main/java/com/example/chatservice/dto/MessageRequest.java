package com.example.chatservice.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private String msg;
    private long receiverId;
    private long senderId;
}
