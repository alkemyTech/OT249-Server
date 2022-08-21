
package com.alkemy.ong.controller;

import com.alkemy.ong.service.impl.AmazonClientImpl;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
public class BucketController {

    private final AmazonClientImpl amazonClientImpl;

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return amazonClientImpl.uploadFile(file);
    }
}
