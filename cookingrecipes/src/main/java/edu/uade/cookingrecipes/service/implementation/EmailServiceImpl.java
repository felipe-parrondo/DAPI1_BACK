package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    public void sendEmail (String target, String subject, String body) {
        JavaMailSender emailSender = getJavaMailSender();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("receteando.noreply@gmail.com");
        message.setTo(target);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
    }

    private JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("feliparrondobastos@gmail.com");
        mailSender.setPassword("pluc ijep gdbf zklh");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        return mailSender;
    }
}
