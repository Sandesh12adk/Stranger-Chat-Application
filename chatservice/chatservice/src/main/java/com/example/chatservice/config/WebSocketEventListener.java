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
        String country = headers.getFirstNativeHeader("country");
        String gender = headers.getFirstNativeHeader("gender");

        if (userIdStr != null) {
            try {
                long userId = Long.parseLong(userIdStr);
                System.out.println("✅ User connected: " + userId);

                // STORE THE USER INFO IN SESSION ATTRIBUTES
                headers.getSessionAttributes().put("userId", userId);
                headers.getSessionAttributes().put("userName", userName);
                headers.getSessionAttributes().put("country", country);
                headers.getSessionAttributes().put("gender", gender);

                // Update status in database
                userServiceClient.setStatus(userId, "ONLINE");
                System.out.println(userId + "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
                // BROADCAST STATUS CHANGE TO ALL CLIENTS
                broadcastUserStatusChange(userId, userName, country, gender, "ONLINE");

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

        // Get user info from session attributes (that we stored during connect)
        Long userId = (Long) headers.getSessionAttributes().get("userId");
        String userName = (String) headers.getSessionAttributes().get("userName");
        String country = (String) headers.getSessionAttributes().get("country");
        String gender = (String) headers.getSessionAttributes().get("gender");

        if (userId != null) {
            System.out.println("❌ User disconnected: " + userId);

            // Update status in database
            userServiceClient.setStatus(userId, "OFFLINE");

            // BROADCAST STATUS CHANGE TO ALL CLIENTS
            broadcastUserStatusChange(userId, userName, country, gender, "OFFLINE");

        } else {
            logger.warn("User disconnected but userId was not found in session");
        }
    }

    private void broadcastUserStatusChange(Long userId, String userName, String country, String gender, String status) {
        try {
            // Create status update message with all user info
            java.util.Map<String, Object> statusUpdate = new java.util.HashMap<>();
            statusUpdate.put("userId", userId);
            statusUpdate.put("userName", userName);
            statusUpdate.put("country", country);
            statusUpdate.put("gender", gender);
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