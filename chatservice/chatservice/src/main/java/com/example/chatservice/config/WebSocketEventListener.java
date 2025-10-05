package com.example.chatservice.config;

import com.example.chatservice.service.UserServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        String userIdStr = headers.getFirstNativeHeader("userId");
        String userName = headers.getFirstNativeHeader("userName");


        if (userIdStr != null) {
            try {
                long userId = Long.parseLong(userIdStr);
                System.out.println("✅ User connected: " + userId);

                // STORE THE USERID IN SESSION ATTRIBUTES
                headers.getSessionAttributes().put("userId", userId);
                headers.getSessionAttributes().put("userName", userName);;

                // Update status in database
                userServiceClient.setStatus(userId, "ONLINE");
                System.out.println(userId + "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
                // BROADCAST STATUS CHANGE TO ALL CLIENTS

            } catch (NumberFormatException e) {
                logger.error("Invalid userId format: {}", userIdStr);
            }
        } else {
            logger.error("No userId provided in WebSocket connection");
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());

        // Get userId from session attributes (that we stored during connect)
        Long userId = (Long) headers.getSessionAttributes().get("userId");
        String userName = (String) headers.getSessionAttributes().get("userName");

        if (userId != null) {
            System.out.println("❌ User disconnected: " + userId);

            // Update status in database
            userServiceClient.setStatus(userId, "OFFLINE");

            // BROADCAST STATUS CHANGE TO ALL CLIENTS
            broadcastUserStatusChange(userId, userName, "OFFLINE");

        } else {
            logger.warn("User disconnected but userId was not found in session");
        }
    }

    private void broadcastUserStatusChange(Long userId, String userName, String status) {
        try {
            // Create status update message
            java.util.Map<String, Object> statusUpdate = new java.util.HashMap<>();
            statusUpdate.put("userId", userId);
            statusUpdate.put("userName", userName);
            statusUpdate.put("status", status);
            statusUpdate.put("timestamp", java.time.LocalDateTime.now().toString());
            statusUpdate.put("type", "USER_STATUS_UPDATE");
            // Broadcast to ALL connected clients
            messagingTemplate.convertAndSend("/topic/status-change", statusUpdate);
            System.out.println("📢 Broadcasted status update: " + userName + " is " + status);

        } catch (Exception e) {
            logger.error("Error broadcasting status change: {}", e.getMessage());
        }
    }
}