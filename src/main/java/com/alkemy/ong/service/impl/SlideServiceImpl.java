package com.alkemy.ong.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.alkemy.ong.exceptions.RecordException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ong.Utils.CustomMultipartFile;
import com.alkemy.ong.dto.PublicOrganizationDto;
import com.alkemy.ong.dto.SlideResponseDto;
import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.dto.SlideRequestDto;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.AmazonClient;
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

    @Autowired
    AmazonClient amazonClient;

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
        Slide slide = slideRepository.findById(id).orElseThrow(() -> new RecordException.RecordNotFoundException( "Slide not found" ));
        slideRepository.delete(slide);
    }

    @Override
    public Slide update(String id, Slide slide) {
        return null;
    }

    @Override
    public SlideResponseDto save(SlideRequestDto slideRequestDto) {
        CustomMultipartFile customMultipartFile = new CustomMultipartFile();
        String fileUrl = amazonClient.uploadFile(customMultipartFile.base64ToMultipart(slideRequestDto.getBase64Img()));
        if(slideRequestDto.getPosition() == null) {
            slideRequestDto.setPosition(this.lastPosition() + 1);
        }
        Slide entity = modelMapper.map(slideRequestDto, Slide.class);
        entity.setOrganization(organizationService.get(slideRequestDto.getOrgId()));
        entity.setImageUrl(fileUrl);
        slideRepository.save(entity);
        SlideResponseDto dtoResponse = modelMapper.map(entity, SlideResponseDto.class);
        dtoResponse.setPublicOrganizationDto(modelMapper.map(entity.getOrganization(), PublicOrganizationDto.class));
        return dtoResponse;
    }

    public Integer lastPosition(){
        return slideRepository.findTopByOrderByPositionDesc().getPosition();
    }

    
}
