package com.smartwater.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;


    public void sendSimpleMail(String wrongMessage){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2775153204@qq.com");
        message.setTo("752359476@qq.com");
        message.setSubject("SmartWater Warning");
        message.setText(wrongMessage);
        mailSender.send(message);

    }
}
