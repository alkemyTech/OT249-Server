package com.alkemy.ong.controller;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.service.TestimonialService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/testimonials")
@AllArgsConstructor
public class TestimonialController {
    private final TestimonialService testimonialService;
    @PostMapping("")
    public ResponseEntity<TestimonialDto> createTestimony(@Valid @RequestBody TestimonialDto testimonialDto, BindingResult bindingResult) {

        TestimonialDto newTestimonialDto = testimonialService.createTestimony( testimonialDto, bindingResult);
        return new ResponseEntity<>( newTestimonialDto, HttpStatus.CREATED);
    }


}
