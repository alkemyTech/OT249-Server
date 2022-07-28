package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.exceptions.BindingResultException;
import com.alkemy.ong.model.Testimonial;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.TestimonialService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@AllArgsConstructor
public class TestimonialServiceImpl implements TestimonialService {

    private final TestimonialRepository testimonialRepository;
    private final ModelMapper modelMapper;

    @Override
    public boolean deleteTestimony(String id) {

        return false;
    }

    @Override
    public TestimonialDto updateTestimony(String id, TestimonialDto newsDTO, BindingResult bindingResult) {

        return null;
    }

    @Override
    public TestimonialDto createTestimony(TestimonialDto testimonialDto, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors())
            throw new BindingResultException( bindingResult );
        Testimonial testimonial = testimonialRepository.save( modelMapper.map( testimonialDto, Testimonial.class ) );
        return modelMapper.map( testimonial, TestimonialDto.class);
    }
}
