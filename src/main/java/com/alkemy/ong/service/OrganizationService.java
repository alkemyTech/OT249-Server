package com.alkemy.ong.service;

import com.alkemy.ong.model.Organization;
import com.alkemy.ong.dto.PublicOrganizationDto;

import java.util.List;
import java.util.UUID;

public interface OrganizationService{
    PublicOrganizationDto getPublicData();
    List<Organization> getAll();
    Organization get(UUID id);
    void delete(UUID id);
    Organization update(UUID id, Organization organization);
}
