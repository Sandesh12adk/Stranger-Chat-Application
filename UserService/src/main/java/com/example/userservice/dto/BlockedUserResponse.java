package com.example.userservice.dto;

import com.example.userservice.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

@Data
public class BlockedUserResponse {
    private long id;
    private long blockedTo;
    private long blockedBy;
    private LocalDateTime blockedDateAndTime;
}
