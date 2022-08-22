package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.PublicOrganizationDto;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.OrganizationService;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService{

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public PublicOrganizationDto getPublicData() {
        Organization organization = organizationRepository.findAll().get(0);
        //PublicOrganizationDto publicOrganizationDto = new PublicOrganizationDto();
        //publicOrganizationDto.setName(organization.getName());
        //publicOrganizationDto.setAddress(organization.getAddress());
        //publicOrganizationDto.setPhone(organization.getPhone());
        //publicOrganizationDto.setImage(organization.getImage());
        return modelMapper.map(organization, PublicOrganizationDto.class);
    }

    @Override
    public List<Organization> getAll() {
        return null;
    }

    @Override
    public Organization get(String id) {
        return organizationRepository.getById(id);
    }

    @Override
    public Organization delete(String id) {
    	return null;
    }

    @Override
    public PublicOrganizationDto update(String id, PublicOrganizationDto publicOrganizationDto) {
        Organization organization = organizationRepository.findById(id).get();
        modelMapper.map(publicOrganizationDto,organization);
        organizationRepository.save(organization);
        return publicOrganizationDto;
    }
}
