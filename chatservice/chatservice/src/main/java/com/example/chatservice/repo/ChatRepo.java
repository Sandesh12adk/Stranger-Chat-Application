package com.example.chatservice.repo;

import com.example.chatservice.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRepo extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByUserAAndUserBOrUserBAndUserA(
            Long userA, Long userB,
            Long userA2, Long userB2
    );

}
