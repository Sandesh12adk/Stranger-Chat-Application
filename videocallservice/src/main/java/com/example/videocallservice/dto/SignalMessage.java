package com.example.videocallservice.dto;

import lombok.Data;

@Data
public class SignalMessage {
    private String type; // offer, answer, candidate
    private Long from;
    private Long to;
    private Object sdp;
    private Object candidate;

}
