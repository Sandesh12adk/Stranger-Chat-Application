package com.example.randompairchat.service;
import org.springframework.stereotype.Service;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class PairingService {

    // Queue of waiting users
    private final Queue<Long> waitingQueue = new ConcurrentLinkedQueue<>();

    // Active pairs (userId -> partnerId)
    private final Map<Long, Long> pairedUsers = new ConcurrentHashMap<>();

    /**
     * When a user joins:
     *  - If the queue is empty → add them to queue and wait.
     *  - If someone is waiting → remove that user and pair them.
     */
    public synchronized Long connect(Long userId) {
        // If no one waiting → add to queue
        if (waitingQueue.isEmpty()) {
            waitingQueue.add(userId);
            System.out.println(userId + " is waiting for a partner...");
            return null;
        } else {
            // someone is waiting → pair them
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

    /**
     * Disconnect user (either from waiting queue or active pair)
     */
    public synchronized void disconnect(Long userId) {
        waitingQueue.remove(userId);
        Long partnerId = pairedUsers.remove(userId);
        if (partnerId != null) {
            pairedUsers.remove(partnerId);
       //     waitingQueue.add(partnerId); // partner goes back to waiting dont do this wait partnet will click next button himself
            //for new connection
            System.out.println(userId + " disconnected. " + partnerId + " returned to waiting queue.");
        } else {
            System.out.println(userId + " removed from waiting queue.");
        }
    }

    /**
     * Get current partner of user
     */
    public Long getPartner(Long userId) {
        return pairedUsers.get(userId);
    }
}

