package com.example.chatservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatLimitService {
    @Value("${messageLimit}")
    private int messageLimit;
    private ConcurrentHashMap<String, Integer> senderMessageCount= new ConcurrentHashMap<>();
    public void increaseMessageCount(long senderId, long receiverId){
        String key= senderId+":"+receiverId;
        senderMessageCount.put(key,senderMessageCount.getOrDefault(key,0)+1);
    }
    public Boolean canSend(long senderId, long receiverId){
        String key= senderId+":"+receiverId;
        int count= senderMessageCount.getOrDefault(key,0);
        System.out.println(count+" "+"This is the count from message limit server okay my man"+" "+messageLimit);
        return count>=messageLimit? false: true;
    }
    public void setMessageCountPermanentlyZero(long senderId, long receiverId){
        String key= senderId+":"+receiverId;
        senderMessageCount.remove(key);  // Becaue you can see in the can send method if no key is present
        //the default value is 0 always;
    }
}
