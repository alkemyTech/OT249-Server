package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.exceptions.BindingResultException;
import com.alkemy.ong.exceptions.RecordException;
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
    public TestimonialDto updateTestimony(String id, TestimonialDto testimonialDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors())
            throw new BindingResultException( bindingResult );
        Testimonial found = testimonialRepository.findById( id ).orElseThrow( () -> new RecordException.RecordNotFoundException( "Testimonial not found" ) );

        TestimonialDto mappedTestimonialDto = this.modelMapper.map( found, TestimonialDto.class );
        mappedTestimonialDto.setContent( testimonialDto.getContent() );
        mappedTestimonialDto.setImage( testimonialDto.getImage() );
        mappedTestimonialDto.setName( testimonialDto.getName() );
        Testimonial testimonialToSave = this.modelMapper.map( mappedTestimonialDto, Testimonial.class );
        Testimonial savedTestimonial = testimonialRepository.save( testimonialToSave );
        return this.modelMapper.map( savedTestimonial, TestimonialDto.class );
    }

    @Override
    public TestimonialDto createTestimony(TestimonialDto testimonialDto, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors())
            throw new BindingResultException( bindingResult );
        Testimonial testimonial = testimonialRepository.save( modelMapper.map( testimonialDto, Testimonial.class ) );
        return modelMapper.map( testimonial, TestimonialDto.class);
    }
}
