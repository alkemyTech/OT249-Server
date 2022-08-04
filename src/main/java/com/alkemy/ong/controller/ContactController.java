package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.model.Contact;
import com.alkemy.ong.service.ContactService;
import lombok.AllArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contacts")
@AllArgsConstructor
public class ContactController {


    private ContactService contactService;

    @PostMapping()
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    ResponseEntity<ContactDto> saveContact(@Valid @RequestBody ContactDto contactDto){
        return new ResponseEntity<>(contactService.saveContact(contactDto),HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<ContactDto>> getContacts(){
        return new ResponseEntity<>(contactService.getAllContacts(), HttpStatus.OK);
    }
}
