package com.alkemy.ong.service;

import org.springframework.web.multipart.MultipartFile;

public interface AWSS3Service {
    
    String uploadFile(MultipartFile file);

}
