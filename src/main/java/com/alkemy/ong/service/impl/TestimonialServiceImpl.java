package com.alkemy.ong.service.impl;

import com.alkemy.ong.utils.PageUtils;
import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.exceptions.BindingResultException;
import com.alkemy.ong.exceptions.RecordException;
import com.alkemy.ong.model.Testimonial;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.TestimonialService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@AllArgsConstructor
public class TestimonialServiceImpl implements TestimonialService {

    private final TestimonialRepository testimonialRepository;
    private final ModelMapper modelMapper;

    @Override
    public void deleteTestimony(String id) {

        Testimonial testimonial = testimonialRepository.findById( id ).orElseThrow( () -> new RecordException.RecordNotFoundException( "Testimony Not found" ) );
        testimonialRepository.delete( testimonial );
    }

    @Override
    public TestimonialDto updateTestimony(String id, TestimonialDto testimonialDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors())
            throw new BindingResultException( bindingResult );
        TestimonialDto mappedTestimonialDto = this.findById( id );
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

    @Override
    public TestimonialDto findById(String id) {

        Testimonial found = testimonialRepository.findById( id ).orElseThrow( () -> new RecordException.RecordNotFoundException( "Testimonial not found" ) );
        return this.modelMapper.map( found, TestimonialDto.class );
    }

	@Override
	public PageDto<TestimonialDto> getAllTestimonials(int page, String order) {

		  Page<Testimonial> testimonialPage = testimonialRepository.findAll( PageUtils.getPageable( page, order ) );
	        Page<TestimonialDto> testimonialDTOPage = testimonialPage.map( testimonial -> modelMapper.map( testimonial, TestimonialDto.class ) );
	        return PageUtils.getPageDto( testimonialDTOPage, "testimonials" );
	}
}
