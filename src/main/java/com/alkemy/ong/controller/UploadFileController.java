package com.alkemy.ong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alkemy.ong.service.AWSS3Service;

@RestController
@RequestMapping("/s3")
public class UploadFileController {
    
    @Autowired
    private AWSS3Service awss3Service;

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestPart(value="file") MultipartFile file){
        awss3Service.uploadFile(file);
        String response = "El archivo " + file.getOriginalFilename() + " fue cargado correctamente a s3";
        return new ResponseEntity<>(response, HttpStatus.OK); 
    }
}
