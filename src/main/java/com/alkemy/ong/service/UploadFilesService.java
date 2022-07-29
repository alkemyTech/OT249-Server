package com.alkemy.ong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFilesService {
    
    @Autowired
    private AmazonClient amazonClient;

    public String image(MultipartFile multipartFile){
        return amazonClient.uploadFile(multipartFile);
    }

}
