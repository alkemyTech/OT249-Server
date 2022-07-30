package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.model.Contact;
import com.alkemy.ong.service.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    ResponseEntity<List<ContactDto>> getContacts(){
        return new ResponseEntity<>(contactService.getAllContacts(), HttpStatus.OK);
    }
}
