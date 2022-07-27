package com.alkemy.ong.controller;


import com.alkemy.ong.dto.PublicOrganizationDto;
import com.alkemy.ong.service.impl.OrganizationServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @PutMapping("/organization/public/{id}")
    ResponseEntity<PublicOrganizationDto> updatePublicOrganization(@PathVariable(name = "id") String id, @Valid @RequestBody PublicOrganizationDto publicOrganizationDto){
        return new ResponseEntity<>(organizationService.update(id, publicOrganizationDto), HttpStatus.ACCEPTED);
    }

}
