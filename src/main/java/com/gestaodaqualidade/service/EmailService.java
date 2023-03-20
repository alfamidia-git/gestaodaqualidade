package com.gestaodaqualidade.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void enviarEmail(String para, String corpo, String assunto){

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(para);
        mailMessage.setText(corpo);
        mailMessage.setSubject(assunto);

        this.javaMailSender.send(mailMessage);
    }
}
