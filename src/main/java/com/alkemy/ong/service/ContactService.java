package com.alkemy.ong.service;

import com.alkemy.ong.model.Contact;

import java.util.List;

public interface ContactService {
    Contact getContact();
    Contact saveContact();
    List<Contact> getAllContacts();
    void deleteContact();
}
