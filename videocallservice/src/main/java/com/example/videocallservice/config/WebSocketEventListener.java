package com.example.videocallservice.config;

import com.example.videocallservice.service.PairingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;

@Component
public class WebSocketEventListener {

    @Autowired
    private PairingService pairingService;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        String userIdStr = headers.getFirstNativeHeader("userId");

        if (userIdStr != null) {
            try {
                long userId = Long.parseLong(userIdStr);
                logger.info("✅ User connected via WebSocket: {}", userId);
                // Store in session attributes for reliable retrieval
                headers.getSessionAttributes().put("userId", userId);

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

        // Try multiple ways to get userId for reliability
        Long userId = getUserIdFromDisconnectEvent(headers);

        if (userId != null) {
            logger.info("❌ User disconnected via WebSocket: {}", userId);
            pairingService.disconnect(userId);
        } else {
            logger.warn("Could not determine userId from disconnect event");
        }
    }

    private Long getUserIdFromDisconnectEvent(StompHeaderAccessor headers) {
        // Method 1: Try session attributes (most reliable)
        Long userId = (Long) headers.getSessionAttributes().get("userId");

        // Method 2: Try to parse from native headers as fallback
        if (userId == null) {
            String userIdStr = headers.getFirstNativeHeader("userId");
            if (userIdStr != null) {
                try {
                    userId = Long.parseLong(userIdStr);
                } catch (NumberFormatException e) {
                    logger.error("Invalid userId in native headers: {}", userIdStr);
                }
            }
        }

        // Method 3: Try to extract from simpSessionAttributes
        if (userId == null) {
            Map<String, Object> sessionAttrs = headers.getSessionAttributes();
            if (sessionAttrs != null) {
                userId = (Long) sessionAttrs.get("userId");
            }
        }

        return userId;
    }
}
