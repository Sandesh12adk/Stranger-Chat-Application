package com.example.chatservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class MessageResponse {

    private long id;
    private String msg;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalDateTime sentTime;
    private long receiverId;
    private long senderId;

}
