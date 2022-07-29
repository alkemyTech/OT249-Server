package com.alkemy.ong.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@AllArgsConstructor
public class ContactController {


    private ContactService contactService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<Contact>> getContacts(){
        return new ResponseEntity<>(ContactService.getAllContacts());
    }
}
