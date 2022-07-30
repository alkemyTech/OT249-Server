package com.alkemy.ong.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ong.dto.PublicOrganizationDto;
import com.alkemy.ong.dto.SlideResponseDto;
import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.dto.SlideRequestDto;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.OrganizationService;
import com.alkemy.ong.service.SlideService;

@Service
public class SlideServiceImpl implements SlideService {

    @Autowired
    private SlideRepository slideRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrganizationService organizationService;

    @Override
    public List<SlideDto> getAll() {
        List<SlideDto> dtos = new ArrayList<>();
        slideRepository.findAll().forEach(entity -> dtos.add(modelMapper.map(entity, SlideDto.class)));
        return dtos;
    }

    @Override
    public SlideResponseDto getById(String id) {
        Slide entity = slideRepository.getById(id);
        SlideResponseDto dto = modelMapper.map(entity, SlideResponseDto.class);
        dto.setPublicOrganizationDto(modelMapper.map(entity.getOrganization(), PublicOrganizationDto.class));
        return dto;
    }

    @Override
    public void delete(String id) {
    }

    @Override
    public Slide update(String id, Slide slide) {
        return null;
    }

    @Override
    public SlideResponseDto save(SlideRequestDto slideRequestDto) {
        Slide entity = modelMapper.map(slideRequestDto, Slide.class);
        entity.setOrganization(organizationService.get(slideRequestDto.getOrgId()));
        SlideResponseDto dto = modelMapper.map(entity, SlideResponseDto.class);
        dto.setPublicOrganizationDto(modelMapper.map(entity.getOrganization(), PublicOrganizationDto.class));
        return dto;
    }

    
}
