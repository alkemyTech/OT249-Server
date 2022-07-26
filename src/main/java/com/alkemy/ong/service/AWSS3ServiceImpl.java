package com.alkemy.ong.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AWSS3ServiceImpl implements AWSS3Service{

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AWSS3ServiceImpl.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile file) {
        File mainFile = new File(file.getOriginalFilename());
        try(FileOutputStream stream = new FileOutputStream(mainFile)) {
            stream.write(file.getBytes());
            String newFileName = System.currentTimeMillis() + " " +mainFile.getName();
            LOGGER.info("Subiendo archivo con el nombre {newFileName}");
            PutObjectRequest request = new PutObjectRequest(bucketName, newFileName, mainFile);
            amazonS3.putObject(request);
            return amazonS3.getUrl(bucketName, newFileName).toExternalForm();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
    
}
