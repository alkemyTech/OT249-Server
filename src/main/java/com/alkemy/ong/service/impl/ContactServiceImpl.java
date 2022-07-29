package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.model.Contact;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.service.ContactService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {

    ModelMapper modelMapper;
    ContactRepository contactRepository;
    @Override
    public Contact getContact() {
        return null;
    }

    @Override
    public Contact saveContact() {
        return null;
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
