package com.example.chatservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Primary Key

    @NotBlank(message = "Message cannot be empty")
    @Column(nullable = false)
    private String msg;

    @CreationTimestamp
    @Column(name = "sent_date_time", nullable = false, updatable = false)
    private LocalDateTime sentDateAndTime;

    private long receiverId;
    private long senderId;

    @ManyToOne
    @JoinColumn(name = "chat_room_id", nullable = false)  // FK column in message table
    private ChatRoom chatRoom;
}
