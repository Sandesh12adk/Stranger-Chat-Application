package com.example.chatservice.mapper;

import com.example.chatservice.dto.MessageResponse;
import com.example.chatservice.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageToMessageResponse {
    public MessageResponse messageToMessageResponse(Message message){
        MessageResponse messageResponse= new MessageResponse();
        if(message.getSenderId()!=0 && message.getReceiverId()!= 0) {
            messageResponse.setMsg(message.getMsg());
            messageResponse.setSenderId(message.getSenderId());
            messageResponse.setReceiverId(message.getReceiverId());
            messageResponse.setId(message.getId());
        }
        return messageResponse;
    }
}
