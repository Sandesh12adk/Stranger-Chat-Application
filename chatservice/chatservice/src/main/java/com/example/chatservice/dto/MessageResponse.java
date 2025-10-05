package com.example.chatservice.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
public class MessageResponse {

    private long id;
    private String msg;
    private LocalDateTime sentDateAndTime;
    private long receiverId;
    private long senderId;

}
