package com.gmail.verification.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service

public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    private final Map<String,String > map = new HashMap<>();
    Logger log = LoggerFactory.getLogger((MailService.class));
    public void sendMail(String userId,String email){
        log.info("SendMail Start!");
        String otp = generateOtp();
        String subject = "Your One Time Password (OTP)";
        String message = "Dear User,\n\nYour OTP is: " + otp;
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        map.put(userId,otp);
        log.info("Otp stored");
        javaMailSender.send(simpleMailMessage);
        log.info("SendMAil End!");
    }
    public String generateOtp(){
        log.info("generateOtp Start!");
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String id = String.format("%06d",number);
        log.info("Otp generated");
        return id;
    }

    public boolean checkOtp(String userId,String otp){
        log.info("checkOtp start");
        String mailOtp = map.get(userId);
        return mailOtp != null && mailOtp.equals(otp);
    }
}
