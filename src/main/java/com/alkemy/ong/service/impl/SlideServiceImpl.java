package com.alkemy.ong.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.SlideService;

@Service
public class SlideServiceImpl implements SlideService {

    @Autowired
    private SlideRepository slideRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<SlideDto> getAll() {
        List<SlideDto> dtos = new ArrayList<>();
        slideRepository.findAll().forEach(entity -> dtos.add(modelMapper.map(entity, SlideDto.class)));
        return dtos;
    }

    @Override
    public Slide get(String id) {
        return null;
    }

    @Override
    public void delete(String id) {
    }

    @Override
    public Slide update(String id, Slide slide) {
        return null;
    }
    
}
