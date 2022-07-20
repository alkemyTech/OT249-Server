package com.alkemy.ong.service;

import com.alkemy.ong.model.Organization;
import com.alkemy.ong.model.PublicOrganization;
import com.alkemy.ong.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class OrganizationServiceImpl implements OrganizationService{

    @Autowired
    private OrganizationRepository organizationRepository;


    @Override
    public PublicOrganization getPublicData() {
        Organization organization = organizationRepository.findAll().get(0);
        PublicOrganization publicOrganization = new PublicOrganization();
        publicOrganization.setName(organization.getName());
        publicOrganization.setAddress(organization.getAddress());
        publicOrganization.setPhone(organization.getPhone());
        publicOrganization.setImage(organization.getImage());
        return publicOrganization;
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
}
