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

    /** Sent when an admin approves a Charitable Home (NGO) registration. */
    public void sendNgoApprovedEmail(String toEmail, String ngoName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("SETU - Your Charitable Home Registration is Approved");
        message.setText("Dear " + ngoName + ",\n\n" +
                "Great news! Your Charitable Home registration on SETU has been reviewed and APPROVED by our admin team.\n\n" +
                "You can now log in to your account and start posting requirements to receive donations.\n\n" +
                "Thank you for joining SETU.\n\n" +
                "- Team SETU");
        mailSender.send(message);
    }

    /** Sent when an admin rejects a Charitable Home (NGO) registration. */
    public void sendNgoRejectedEmail(String toEmail, String ngoName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("SETU - Your Charitable Home Registration was Rejected");
        message.setText("Dear " + ngoName + ",\n\n" +
                "We're sorry to inform you that your Charitable Home registration on SETU was not approved after admin review. " +
                "This is usually because the uploaded verification document could not be confirmed as valid.\n\n" +
                "If you believe this is a mistake, you're welcome to register again with a clearer registration certificate or proof document.\n\n" +
                "- Team SETU");
        mailSender.send(message);
    }

 // naya method: pending registration ka email (NGO)
    public void sendNgoPendingEmail(String toEmail, String ngoName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("SETU - Registration Received, Under Review");
        message.setText("Dear " + ngoName + ",\n\n" +
                "Thank you for registering your Charitable Home on SETU.\n\n" +
                "Your account is currently PENDING admin verification. We'll review your " +
                "documents and notify you by email as soon as a decision is made.\n\n" +
                "- Team SETU");
        mailSender.send(message);
    }

    // naya method: pending registration ka email (Volunteer)
    public void sendVolunteerPendingEmail(String toEmail, String volunteerName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("SETU - Registration Received, Under Review");
        message.setText("Dear " + volunteerName + ",\n\n" +
                "Thank you for registering as a Volunteer on SETU.\n\n" +
                "Your account is currently PENDING admin approval. We'll notify you by " +
                "email as soon as a decision is made.\n\n" +
                "- Team SETU");
        mailSender.send(message);
    }

    // naya method: Volunteer reject hone par
    public void sendVolunteerRejectedEmail(String toEmail, String volunteerName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("SETU - Your Volunteer Registration was Rejected");
        message.setText("Dear " + volunteerName + ",\n\n" +
                "We're sorry to inform you that your Volunteer registration on SETU was " +
                "not approved after admin review.\n\n" +
                "If you believe this is a mistake, you're welcome to register again.\n\n" +
                "- Team SETU");
        mailSender.send(message);
    }
    
    /** Sent when an admin approves a Volunteer registration. */
    public void sendVolunteerApprovedEmail(String toEmail, String volunteerName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("SETU - Your Volunteer Account is Approved");
        message.setText("Dear " + volunteerName + ",\n\n" +
                "Your Volunteer account on SETU has been reviewed and APPROVED by our admin team.\n\n" +
                "You can now log in and start viewing your assignments.\n\n" +
                "- Team SETU");
        mailSender.send(message);
    }
}