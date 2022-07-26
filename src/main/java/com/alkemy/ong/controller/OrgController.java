package com.alkemy.ong.controller;


import com.alkemy.ong.dto.PublicOrganizationDto;
import com.alkemy.ong.service.OrganizationService;
import com.alkemy.ong.service.OrganizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrgController {
    @Autowired
    private OrganizationServiceImpl organizationService;
    @GetMapping("/organization/public")
    ResponseEntity<PublicOrganizationDto> getPublicDataConfig(){
        return new ResponseEntity<>(organizationService.getPublicData(), HttpStatus.OK);
    }
    @PutMapping("/organization/public")
    ResponseEntity<PublicOrganizationDto> updatePublicOrganization(@Valid @RequestBody PublicOrganizationDto publicOrganizationDto){
        return new ResponseEntity<>(organizationService.updatePublicOrg(publicOrganizationDto), HttpStatus.ACCEPTED);
    }
}
