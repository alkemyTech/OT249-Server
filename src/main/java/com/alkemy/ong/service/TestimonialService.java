package com.alkemy.ong.service;

import com.alkemy.ong.dto.TestimonialDto;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

public interface TestimonialService {
    void deleteTestimony(String id);
    TestimonialDto updateTestimony(String id, TestimonialDto testimonialDto, BindingResult bindingResult);

    TestimonialDto createTestimony(@Valid TestimonialDto testimonialDto, BindingResult bindingResult);

    TestimonialDto findById(String id);
}
