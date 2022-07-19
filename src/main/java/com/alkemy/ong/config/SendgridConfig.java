package com.alkemy.ong.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sendgrid.SendGrid;

@Configuration
public class SendgridConfig {
    
    @Value("${sendgrid.key}")
    private String sgKey;

    @Bean
    public SendGrid getSendGrid(){
        return new SendGrid(sgKey);
    }

}
