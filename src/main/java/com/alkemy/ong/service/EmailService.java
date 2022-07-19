package com.alkemy.ong.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

    @Value("${sendgrid.from}")
    private String from;

    @Autowired
    private SendGrid sendGrid;

    public Response sendEmail(String subject, String emailTo, String emailContent) throws IOException{
        
        Email from = new Email(this.from);
        Email to = new Email(emailTo);
        Content content = new Content("text/plain", emailContent);
        Mail mail = new Mail(from, subject, to, content);

        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            return sendGrid.api(request);
        } catch (IOException ex) {
            log.error("Error al enviar mensaje a travez de sendgrid", ex);
            throw ex;
        }

    }

}