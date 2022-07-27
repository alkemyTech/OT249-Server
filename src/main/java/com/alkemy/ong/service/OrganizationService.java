package com.alkemy.ong.service;

import com.alkemy.ong.dto.PublicOrganizationDto;
import com.alkemy.ong.model.Organization;

import java.util.List;
import java.util.UUID;

public interface OrganizationService{
    PublicOrganizationDto getPublicData();
    List<Organization> getAll();
    Organization get(UUID id);
    void delete(UUID id);
    PublicOrganizationDto update(UUID id, PublicOrganizationDto publicOrganizationDto);
}
