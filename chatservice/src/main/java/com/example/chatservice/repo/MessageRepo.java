package com.example.chatservice.repo;

import com.example.chatservice.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {

        // Fetch messages by sender and receiver
        @Query("SELECT m FROM Message m WHERE " +
                "(m.senderId = :user1 AND m.receiverId = :user2) OR " +
                "(m.senderId = :user2 AND m.receiverId = :user1) " +
                "ORDER BY m.sentDateAndTime DESC")
        List<Message> findChatHistory(long user1, long user2, Pageable pageable);
        boolean existsBySenderIdAndReceiverId(long senderId, long receiverId);

}

