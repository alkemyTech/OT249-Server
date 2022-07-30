package com.alkemy.ong.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.dto.SlideDetailsDto;
import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.service.SlideService;

@RestController
@RequestMapping("/slides")
public class SlideController {

    @Autowired
    private SlideService slideService;
    
    @GetMapping
    @PreAuthorize( "hasRole('ADMIN')")
    public ResponseEntity<List<SlideDto>> getAll(){
        return new ResponseEntity<List<SlideDto>>(slideService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize( "hasRole('ADMIN')")
    public ResponseEntity<SlideDetailsDto> getById(@PathVariable String id){
        return new ResponseEntity<>(slideService.getById(id), HttpStatus.OK);
    }
}
