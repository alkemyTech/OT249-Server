package com.alkemy.ong.service;

import com.sendgrid.Response;
import com.sendgrid.helpers.mail.objects.Content;

import java.io.IOException;

public interface EmailService {
    Response sendEmail(String subject, String emailTo, Content body) throws IOException;
    void sendEmailToContact(String email, String name);
    void WelcomeMail(String emailTo, String firstName);
    String welcomeEmailTemplate(String firstName);

}
