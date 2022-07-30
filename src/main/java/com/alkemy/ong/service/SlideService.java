package com.alkemy.ong.service;

import java.util.List;

import com.alkemy.ong.dto.SlideResponseDto;
import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.dto.SlideRequestDto;
import com.alkemy.ong.model.Slide;

public interface SlideService {
    
    List<SlideDto> getAll();
    SlideResponseDto getById(String id);
    SlideResponseDto save(SlideRequestDto slideRequestDto);
    void delete(String id);
    Slide update(String id, Slide slide);
}
