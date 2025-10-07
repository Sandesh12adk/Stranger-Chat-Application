package com.example.chatservice.controller;
import com.example.chatservice.dto.MessageRequest;
import com.example.chatservice.dto.MessageResponse;
import com.example.chatservice.mapper.MessageToMessageResponse;
import com.example.chatservice.model.ChatRoom;
import com.example.chatservice.model.Message;
import com.example.chatservice.service.ChatLimitService;
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
    private final ChatLimitService chatLimitService;

    public ChatController(ChatService chatService, SimpMessagingTemplate simpMessagingTemplate,
                          MessageService messageService, MessageToMessageResponse messageToMessageResponse,
                          ChatLimitService chatLimitService){
        this.chatService = chatService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService= messageService;
        this.messageToMessageResponse= messageToMessageResponse;
        this.chatLimitService= chatLimitService;
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
            Long receiverId= messageRequest.getReceiverId();

            if(messageRequest.getReceiverId() != 0 && senderId != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("msg", messageRequest.getMsg());
                response.put("senderId", senderId);
                response.put("receiverId", messageRequest.getReceiverId());
                response.put("timestamp", LocalDateTime.now());
                String receiverTopic = "/topic/user/" + messageRequest.getReceiverId();
                String senderTopic = "/topic/user/" + senderId;

                boolean hasReply= messageService.hasReply(receiverId,senderId);
                if(hasReply){
                    chatLimitService.setMessageCountPermanentlyZero(senderId,receiverId); //Set premanently zero
                }
                if(chatLimitService.canSend(senderId,receiverId)) {
                    chatLimitService.increaseMessageCount(senderId,receiverId);
                    // Send to RECEIVER's topic
                    simpMessagingTemplate.convertAndSend(receiverTopic, response);
                    // Also send back to sender for UI update (FIXED PATH)
                    simpMessagingTemplate.convertAndSend(senderTopic, response);
                    messageService.saveMessage(messageRequest);
                }
                else{
                    String message= "System: Please wait for the receiver to respond before sending more messages";
                    response.put("msg", message);
                    response.put("type", "system"); // Add this line
                    response.put("senderId", 0); // Use 0 or null for system messages
                    simpMessagingTemplate.convertAndSend(senderTopic, response);
                }
            }
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
            e.printStackTrace();
        }
    }
    }

