package com.alkemy.ong.service;

import com.alkemy.ong.model.Organization;
import com.alkemy.ong.dto.PublicOrganizationDto;
import com.alkemy.ong.repository.OrganizationRepository;
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


    private OrganizationRepository organizationRepository;
    private ModelMapper modelMapper;



    @Override
    public PublicOrganizationDto getPublicData() {
        Organization organization = organizationRepository.findAll().get(0);
        return modelMapper.map(organization,PublicOrganizationDto.class);
    }

    @Override
    public List<Organization> getAll() {
        return null;
    }

    @Override
    public Organization get(UUID id) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public Organization update(UUID id, Organization organization) {
        return null;
    }

    public PublicOrganizationDto updatePublicOrg(PublicOrganizationDto publicOrganizationDto){
        Organization organization = organizationRepository.findAll().get(0);
        modelMapper.map(publicOrganizationDto, organization);
        organizationRepository.save(organization);
        return publicOrganizationDto;
    }
}
