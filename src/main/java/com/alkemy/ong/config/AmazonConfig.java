package com.alkemy.ong.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonConfig {

    @Value("${amazon.accessKey}")
    private String accessKey;
    @Value("${amazon.secretKey}")
    private String secretKey;
    
    
    // @Bean//amazonS3Client
    // public AmazonS3 getS3Client() {
    //     BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
    //     return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName("us-east-1"))
    //             .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    // }
}
