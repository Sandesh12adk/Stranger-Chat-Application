package com.example.chatservice.service;

import com.example.chatservice.dto.MessageRequest;
import com.example.chatservice.mapper.MessageRequestToMessage;
import com.example.chatservice.model.ChatRoom;
import com.example.chatservice.model.Message;
import com.example.chatservice.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private ChatService chatService;
    public Message saveMessage(MessageRequest messageRequest) {
        ChatRoom chatRoom=chatService
                .findChatRoomBySenderAndReceiverId(messageRequest.getReceiverId(),messageRequest.getSenderId());
        MessageRequestToMessage messageRequestToMessage= new MessageRequestToMessage();
        Message message= messageRequestToMessage.messageRequestToMessage(messageRequest);
        message.setChatRoom(chatRoom);
        message.setSenderId(messageRequest.getSenderId());
        message.setReceiverId(message.getReceiverId());
        message.setSentDateAndTime(LocalDateTime.now());
        return messageRepo.save(message);
    }
    Pageable limit = PageRequest.of(0, 50);
    public List<Message> chatHistory(long senderId,long receiverId){
        return messageRepo.findChatHistory(senderId,receiverId,limit);
    }
}
