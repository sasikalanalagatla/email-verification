package com.gmail.verification.controller;

import com.gmail.verification.model.Otp;
import com.gmail.verification.model.User;
import com.gmail.verification.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class MailController {
    @Autowired
    private MailService mailService;
    @PostMapping("/verification/mail")
    public String sendMail(@RequestBody User user){
        mailService.sendMail(user.getUserId(),user.getEmail());
         return "success";
    }

    @PostMapping("/check/otp")
    public ResponseEntity<String> checkOtp(@RequestBody Otp otp){
        boolean isValid = mailService.checkOtp(otp.getUserId(),otp.getOtp());
        if(isValid){
            return new ResponseEntity<>("valid",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("invalid otp ",HttpStatus.NOT_FOUND);
        }
    }
}
