package com.alkemy.ong.controller;


import com.alkemy.ong.dto.PublicOrganizationDto;
import com.alkemy.ong.dto.SlideResponseDto;
import com.alkemy.ong.service.OrganizationService;
import com.alkemy.ong.service.SlideService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class OrgController {
    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private SlideService slideService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/organization/public")
    ResponseEntity<PublicOrganizationDto> getPublicDataConfig(){
        try {
        	return new ResponseEntity<>(organizationService.getPublicData(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/organization/public/{id}")
    ResponseEntity<PublicOrganizationDto> updatePublicOrganization(@PathVariable(name = "id") String id, @Valid @RequestBody PublicOrganizationDto publicOrganizationDto){
        return new ResponseEntity<>(organizationService.update(id, publicOrganizationDto), HttpStatus.ACCEPTED);
    }
    
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/organization/public/slides/{id}")
    ResponseEntity<List<SlideResponseDto>>slidesList(@PathVariable String id) throws Exception {
        List<SlideResponseDto> slideDtoList = new ArrayList<>();
        slideDtoList= slideService.slideForOng(id);
        return ResponseEntity.ok().body(slideDtoList);

    }

}
