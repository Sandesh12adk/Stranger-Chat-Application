package com.example.chatservice.mapper;

import com.example.chatservice.dto.MessageRequest;
import com.example.chatservice.model.Message;

public class MessageRequestToMessage {
    public Message messageRequestToMessage(MessageRequest messageRequest){
        Message message= new Message();
        message.setMsg(messageRequest.getMsg());
        message.setReceiverId(messageRequest.getReceiverId());
        message.setSenderId(messageRequest.getSenderId());
        return message;
    }
}
