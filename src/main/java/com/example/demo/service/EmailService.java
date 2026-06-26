package com.example.demo.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class EmailService
{
    private final JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) 
    {
        this.mailSender = mailSender;
    }

    @Async("taskExecutor")
    public void sendEmail(String to, String subject, String body)
    {
        try
        {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            logger.info("Sending plain-text email to {}", to);

            mailSender.send(message);

            logger.info("plain-text email sent successfully to {}", to);
        }
        catch (Exception e) 
        {
            logger.error("Failed to send plain-text email to {}", to, e);
        }
        
    }

    @Async("taskExecutor")
    public void sendHtmlEmail(String to, String subject, String htmlBody)
    {
        try
        {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            
            logger.info("Sending HTML email to {}", to);

            mailSender.send(message);

            logger.info("HTML email sent successfully to {}", to);
        }
        catch(MessagingException e)
        {
            logger.error("Failed to send HTML email to {}", to, e);

            // will NOT roll back the user-creation transaction
        }
    }

    
}
