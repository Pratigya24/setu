package com.setu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("SETU - Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp +
                "\n\nThis OTP is valid for 5 minutes. If you did not request this, please ignore this email.");
        mailSender.send(message);
    }
}