package com.example.chatservice.service;

import com.example.chatservice.dto.MessageRequest;
import com.example.chatservice.mapper.MessageRequestToMessage;
import com.example.chatservice.model.ChatRoom;
import com.example.chatservice.model.Message;
import com.example.chatservice.repo.ChatRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatService {
private final ChatRepo chatRepo;
private final UserServiceClient userServiceClient;
public ChatService(ChatRepo chatRepo,UserServiceClient userServiceClient
) {
    this.chatRepo= chatRepo;
    this.userServiceClient= userServiceClient;
}
    public ChatRoom findChatRoomBySenderAndReceiverId(long userA, long userB){
    return chatRepo.findByUserAAndUserBOrUserBAndUserA(userA,userB,userB,userA)
            .orElseGet(()->{
                ChatRoom chatRoom= new ChatRoom();
                chatRoom.setCreatedAt(LocalDateTime.now());
                chatRoom.setUserA(userServiceClient.findById(userA).getUserId());
                chatRoom.setUserB(userServiceClient.findById(userB).getUserId());
                return chatRepo.save(chatRoom);
            });
    }
}