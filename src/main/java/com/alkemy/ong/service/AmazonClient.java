package com.alkemy.ong.service;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonClient {
    String uploadFile(MultipartFile multipartFile);
}
