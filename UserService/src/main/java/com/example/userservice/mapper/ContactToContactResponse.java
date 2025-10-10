package com.example.userservice.mapper;

import com.example.userservice.dto.ContactResponse;
import com.example.userservice.model.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactToContactResponse {
    public ContactResponse convert(Contact contact) {
        ContactResponse contactResponse = new ContactResponse();
        contactResponse.setId(contact.getId());
        contactResponse.setMsg(contact.getMsg());
        contactResponse.setSentAt(contact.getSentAt());
        contactResponse.setUserId(contact.getUserId());
        return contactResponse;
    }
}