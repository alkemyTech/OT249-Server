package com.alkemy.ong.controller;


import com.alkemy.ong.model.PublicOrganization;
import com.alkemy.ong.service.OrganizationService;
import com.alkemy.ong.service.OrganizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.GeneratedValue;

@RestController
public class OrgController {
    @Autowired
    private OrganizationService organizationService;

    @GetMapping("organization/public")
    ResponseEntity<PublicOrganization> getPublicDataConfig(){
        return new ResponseEntity<>(organizationService.getPublicData(), HttpStatus.OK);
    }
}
