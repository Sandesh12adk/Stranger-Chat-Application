package com.example.userservice.mapper;

import com.example.userservice.dto.ContactRequest;
import com.example.userservice.model.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactRequestToContact {
    public Contact convert(ContactRequest contactRequest){
        Contact contact= new Contact();
        contact.setMsg(contactRequest.getMessage());
        contact.setUserId(contactRequest.getUserId());
        return contact;
    }
}
