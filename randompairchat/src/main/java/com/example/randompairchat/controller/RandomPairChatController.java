package com.example.randompairchat.controller;

import com.example.randompairchat.dto.Message;
import com.example.randompairchat.service.PairingService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RandomPairChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final PairingService pairingService;

    public RandomPairChatController(SimpMessagingTemplate messagingTemplate,
                                    PairingService pairingService){
        this.messagingTemplate = messagingTemplate;
        this.pairingService = pairingService;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload Message message) {
        Long senderId = message.getSenderId();
        Long receiverId = message.getReceiverId();

        String receiverTopic = "/content/user/" + receiverId;

        // Send to receiver only
        messagingTemplate.convertAndSend(receiverTopic, message);

        System.out.println("Message sent from " + senderId + " to " + receiverId);
    }

    @PostMapping("/next/{userId}")
    public ResponseEntity<Map<String, Object>> next(@PathVariable long userId) {
        Long partnerId = pairingService.connect(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("partnerId", partnerId);

        if (partnerId != null) {
            // Notify both users about the pairing
            notifyPairing(userId, partnerId);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/skip/{userId}")
    public ResponseEntity<Void> skip(@PathVariable long userId) {
        Long partnerId = pairingService.getPartner(userId);
        pairingService.disconnect(userId);

        if (partnerId != null) {
            // Notify the partner about disconnection
            notifyDisconnection(partnerId, userId);
        }

        return ResponseEntity.ok().build();
    }

    private void notifyPairing(Long user1, Long user2) {
        // Notify user1
        Map<String, Object> pairNotification1 = new HashMap<>();
        pairNotification1.put("type", "PAIR_CONNECTED");
        pairNotification1.put("pairedUserId", user2);
        pairNotification1.put("pairedUserName", "User " + user2); // You can fetch actual username if available

        messagingTemplate.convertAndSend("/content/user/" + user1, pairNotification1);

        // Notify user2
        Map<String, Object> pairNotification2 = new HashMap<>();
        pairNotification2.put("type", "PAIR_CONNECTED");
        pairNotification2.put("pairedUserId", user1);
        pairNotification2.put("pairedUserName", "User " + user1); // You can fetch actual username if available

        messagingTemplate.convertAndSend("/content/user/" + user2, pairNotification2);

        System.out.println("Notified pairing: " + user1 + " ↔ " + user2);
    }

    private void notifyDisconnection(Long userId, Long disconnectedPartnerId) {
        Map<String, Object> disconnectNotification = new HashMap<>();
        disconnectNotification.put("type", "PAIR_DISCONNECTED");
        disconnectNotification.put("disconnectedUserId", disconnectedPartnerId);

        messagingTemplate.convertAndSend("/content/user/" + userId, disconnectNotification);

        System.out.println("Notified disconnection to " + userId + " about " + disconnectedPartnerId);
    }
}