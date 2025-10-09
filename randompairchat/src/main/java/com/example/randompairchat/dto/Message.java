package com.example.randompairchat.dto;

import lombok.Data;

@Data
public class Message{
    private String msg;
    private long receiverId;
    private long senderId;
}
