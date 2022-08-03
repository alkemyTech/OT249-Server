
package com.alkemy.ong.controller;

import com.alkemy.ong.service.AmazonClient;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@ApiIgnore
public class BucketController {

    private final AmazonClient amazonClient;

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return amazonClient.uploadFile(file);
    }
}
