package com.example.chatservice.controller;
import com.example.chatservice.dto.MessageRequest;
import com.example.chatservice.dto.MessageResponse;
import com.example.chatservice.mapper.MessageToMessageResponse;
import com.example.chatservice.model.ChatRoom;
import com.example.chatservice.model.Message;
import com.example.chatservice.service.ChatService;
import com.example.chatservice.service.MessageService;
import jakarta.ws.rs.core.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;
    private final MessageToMessageResponse messageToMessageResponse;

    public ChatController(ChatService chatService, SimpMessagingTemplate simpMessagingTemplate,
                          MessageService messageService, MessageToMessageResponse messageToMessageResponse){
        this.chatService = chatService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService= messageService;
        this.messageToMessageResponse= messageToMessageResponse;
    }

    @GetMapping("/get-chat-room")
    public ResponseEntity<ChatRoom> getChatRoom(
            @RequestParam long userA,
            @RequestParam long userB
    ){
        return ResponseEntity.ok(chatService.findChatRoomBySenderAndReceiverId(userA,userB));
    }

    @GetMapping("/get-chat-history/{senderId}/{receiverId}")
    public ResponseEntity<List<MessageResponse>> getChatHistory(@PathVariable long senderId, @PathVariable long receiverId){
        List<Message> messageList= messageService.chatHistory(senderId,receiverId);
        List<MessageResponse> messageResponseList= new ArrayList<>();
        messageList.forEach((message)->{
            messageResponseList.add(messageToMessageResponse.messageToMessageResponse(message));
        });
        return ResponseEntity.ok(messageResponseList);
    }
    @MessageMapping("/chat.send")
    public void sendMessage(@Payload MessageRequest messageRequest) {
        try {
            // Use the senderId from the message request instead of session
            Long senderId = messageRequest.getSenderId();

            if(messageRequest.getReceiverId() != 0 && senderId != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("msg", messageRequest.getMsg());
                response.put("senderId", senderId);
                response.put("receiverId", messageRequest.getReceiverId());
                response.put("timestamp", LocalDateTime.now());
                // Send to RECEIVER's topic
                String receiverTopic = "/topic/user/" + messageRequest.getReceiverId();
                simpMessagingTemplate.convertAndSend(receiverTopic, response);
                // Also send back to sender for UI update (FIXED PATH)
                String senderTopic = "/topic/user/" + senderId;
                simpMessagingTemplate.convertAndSend(senderTopic, response);
                messageService.saveMessage(messageRequest);
            }
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
            e.printStackTrace();
        }
    }
    }

