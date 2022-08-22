package com.alkemy.ong.controller;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.alkemy.ong.dto.SlideResponseDto;
import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.dto.SlideRequestDto;
import com.alkemy.ong.service.SlideService;


@RestController
@RequestMapping("/slides")
@AllArgsConstructor
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
    public ResponseEntity<SlideResponseDto> getById(@PathVariable String id){
        return new ResponseEntity<>(slideService.getById(id), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String>delete(@PathVariable String id){

        slideService.delete(id);
        return new ResponseEntity<>("Slide deleted", HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize( "hasRole('ADMIN')")
    public ResponseEntity<SlideResponseDto> save(@RequestBody SlideRequestDto slideRequestDto){
        return new ResponseEntity<>(slideService.save(slideRequestDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize( "hasRole('ADMIN')")
    public  ResponseEntity<?>update(@PathVariable String id, @RequestBody SlideRequestDto slideRequestDto) {
        try {
            return new ResponseEntity<>(slideService.update(id, slideRequestDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
