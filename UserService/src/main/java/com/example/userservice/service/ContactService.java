package com.example.userservice.service;

import com.example.userservice.dto.ContactRequest;
import com.example.userservice.dto.ContactResponse;
import com.example.userservice.mapper.ContactRequestToContact;
import com.example.userservice.mapper.ContactToContactResponse;
import com.example.userservice.repo.ContactRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class ContactService {
    private final ContactRepo contactRepo;
    private final ContactRequestToContact contactRequestToContact;
    private final ContactToContactResponse contactToContactResponse;
    public ContactService(ContactRepo contactRepo, ContactToContactResponse contactToContactResponse,
                          ContactRequestToContact contactRequestToContact){
        this.contactRepo= contactRepo;
        this.contactRequestToContact= contactRequestToContact;
        this.contactToContactResponse= contactToContactResponse;
    }
    public ContactResponse save(ContactRequest contactRequest){
        return contactToContactResponse.convert(contactRepo.save(contactRequestToContact.convert(contactRequest)));
    }
}
