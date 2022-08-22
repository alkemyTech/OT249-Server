package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.model.Contact;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.service.ContactService;

import com.alkemy.ong.service.EmailService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor

public class ContactServiceImpl implements ContactService {

    ModelMapper modelMapper;
    
    ContactRepository contactRepository;
    
    @Autowired
    private EmailService emailService;
    
    @Override
    public Contact getContact() {
        return null;
    }

    @Override
    public ContactDto saveContact(ContactDto contactDto) {
        Contact contact = new Contact();
        modelMapper.map(contactDto,contact);
        contact.setDeleted(false);
        contactRepository.save(contact);
        emailService.sendEmailToContact(contactDto.getEmail(), contactDto.getName());
        return contactDto;
    }

    @Override
    public List<ContactDto> getAllContacts() {
        List<ContactDto> contacts = new ArrayList<>();
        contactRepository.findAll().forEach(contact -> {
            contacts.add(modelMapper.map(contact, ContactDto.class));
        });
        return contacts;
    }

    @Override
    public void deleteContact() {

    }
}
