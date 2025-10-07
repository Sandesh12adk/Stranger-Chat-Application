package com.example.userservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BlockedUserRequest {
    @NotNull(message = "blockedTo id is required")
    private long blockedTo;
    @NotNull(message = "blockedTo id is required")
    private long blockedBy;
}
