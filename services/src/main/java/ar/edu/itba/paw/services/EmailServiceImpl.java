package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.*;
import javax.mail.internet.MimeMessage;

import java.util.List;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendMessage(List<String> recipients, String title, String body) {

    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void sendMailRequestToOwner(String to, Map<String, String> values) {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("ownerName", values.get("ownerName"));
        thymeleafContext.setVariable("renterName", values.get("renterName"));
        thymeleafContext.setVariable("startDate", values.get("startDate"));
        thymeleafContext.setVariable("endDate", values.get("endDate"));
        thymeleafContext.setVariable("articleName", values.get("articleName"));
        thymeleafContext.setVariable("requestMessage", values.get("requestMessage"));
        thymeleafContext.setVariable("imgSrc", "/resources/image/rentapp-logo.png");
        thymeleafContext.setVariable("callbackUrl", values.get("callbackUrl"));
        String htmlBody = thymeleafTemplateEngine.process("owner-rent-request.html", thymeleafContext);

        sendHtmlMessage(to, "New Rent Request: " + values.get("articleName"), htmlBody);
    }

    @Override
    public void sendMailRequestToRenter(String to, Map<String, String> values) {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("renterName", values.get("renterName"));
        thymeleafContext.setVariable("ownerName", values.get("ownerName"));
        thymeleafContext.setVariable("startDate", values.get("startDate"));
        thymeleafContext.setVariable("endDate", values.get("endDate"));
        thymeleafContext.setVariable("articleName", values.get("articleName"));
        thymeleafContext.setVariable("imgSrc", "/resources/image/rentapp-logo.png");
        thymeleafContext.setVariable("callbackUrl", values.get("callbackUrl"));
        String htmlBody = thymeleafTemplateEngine.process("renter-rent-request.html", thymeleafContext);

        sendHtmlMessage(to, "Request Sent: " + values.get("articleName"), htmlBody);
    }

    @Override
    public void sendMessage(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@rentapp.paw.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
    }
}
