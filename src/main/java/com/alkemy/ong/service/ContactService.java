package com.alkemy.ong.service;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.model.Contact;

import java.util.List;

public interface ContactService {
    Contact getContact();
    ContactDto saveContact(ContactDto contact);
    List<ContactDto> getAllContacts();
    void deleteContact();
}
