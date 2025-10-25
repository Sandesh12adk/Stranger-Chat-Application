package com.example.videocallservice.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class PairingService {
    private final Queue<Long> waitingQueue = new ConcurrentLinkedQueue<>();
    private final Map<Long, Long> pairedUsers = new ConcurrentHashMap<>();
    private final Map<Long, LocalDateTime> userJoinTime = new ConcurrentHashMap<>(); // userId <--> map time
    private static final long MAX_WAIT_TIME_SECONDS = 10;

    public synchronized Long connect(Long userId) {
        disconnect(userId);

        if (waitingQueue.isEmpty() || waitingQueue.peek().equals(userId)) {
            waitingQueue.remove(userId);
            waitingQueue.add(userId);
            userJoinTime.put(userId, LocalDateTime.now());
            System.out.println(userId + " is waiting for a partner...");
            return null;
        } else {
            Long partnerId = waitingQueue.poll();
            if (partnerId != null && !partnerId.equals(userId)) {
                // Remove both users from join time tracking when paired
                userJoinTime.remove(userId);
                userJoinTime.remove(partnerId);

                pairedUsers.put(userId, partnerId);
                pairedUsers.put(partnerId, userId);
                System.out.println("Paired: " + userId + " ↔ " + partnerId);
                return partnerId;
            } else {
                waitingQueue.add(userId);
                userJoinTime.put(userId, LocalDateTime.now()); // Fixed: Add join time here too
                return null;
            }
        }
    }

    public synchronized void disconnect(Long userId) {
        waitingQueue.remove(userId);
        userJoinTime.remove(userId);
        Long partnerId = pairedUsers.remove(userId);
        if (partnerId != null) {
            pairedUsers.remove(partnerId);
            System.out.println(userId + " disconnected. " + partnerId + " is now alone.");
        }
    }

    public Long getPartner(Long userId) {
        return pairedUsers.get(userId);
    }

    @Scheduled(fixedDelay = 3000)
    public synchronized void cleanExpiredWaitingUsers() {
        LocalDateTime now = LocalDateTime.now();
        Iterator<Map.Entry<Long, LocalDateTime>> iterator = userJoinTime.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Long, LocalDateTime> entry = iterator.next();
            Long userId = entry.getKey();
            LocalDateTime joinTime = entry.getValue();

            long secondsWaited = Duration.between(joinTime, now).getSeconds();

            if (secondsWaited > MAX_WAIT_TIME_SECONDS) {
                // Remove from both collections safely
                waitingQueue.remove(userId);
                iterator.remove(); // Safe removal from userJoinTime
                System.out.println("Removed user " + userId + " from waiting queue after " + secondsWaited + " seconds");
            }
        }
    }
}