package com.alkemy.ong.service;

import com.alkemy.ong.dto.PublicOrganizationDto;
import com.alkemy.ong.model.Organization;

import java.util.List;
import java.util.UUID;

public interface OrganizationService{
    PublicOrganizationDto getPublicData();
    List<Organization> getAll();
    Organization get(String id);
    Organization delete(String id);
    PublicOrganizationDto update(String id, PublicOrganizationDto publicOrganizationDto);
}
