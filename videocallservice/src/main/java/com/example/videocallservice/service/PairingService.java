package com.example.videocallservice.service;

import org.springframework.stereotype.Service;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class PairingService {
    private final Queue<Long> waitingQueue = new ConcurrentLinkedQueue<>();
    private final Map<Long, Long> pairedUsers = new ConcurrentHashMap<>();

    public synchronized Long connect(Long userId) {
        disconnect(userId);

        if (waitingQueue.isEmpty() || waitingQueue.peek().equals(userId)) {
            waitingQueue.remove(userId);
            waitingQueue.add(userId);
            System.out.println(userId + " is waiting for a partner...");
            return null;
        } else {
            Long partnerId = waitingQueue.poll();
            if (partnerId != null && !partnerId.equals(userId)) {
                pairedUsers.put(userId, partnerId);
                pairedUsers.put(partnerId, userId);
                System.out.println("Paired: " + userId + " ↔ " + partnerId);
                return partnerId;
            } else {
                waitingQueue.add(userId);
                return null;
            }
        }
    }

    public synchronized void disconnect(Long userId) {
        waitingQueue.remove(userId);
        Long partnerId = pairedUsers.remove(userId);
        if (partnerId != null) {
            pairedUsers.remove(partnerId);
            System.out.println(userId + " disconnected. " + partnerId + " is now alone.");
        }
    }

    public Long getPartner(Long userId) {
        return pairedUsers.get(userId);
    }
}