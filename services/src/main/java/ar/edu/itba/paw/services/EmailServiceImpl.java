package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private static final ClassPathResource LOGO = new ClassPathResource("/image/rentapp-logo.png");

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Autowired
    private JavaMailSender emailSender;

    private static final String resourceName = "logo";

    private static final String baseUrl = "http://localhost:8080";
//    private static final String baseUrl = "http://pawserver.it.itba.edu.ar/paw-2021b-3";

    private void sendHtmlMessage(String to, String subject, String htmlBody) {
        sendHtmlMessage(to, subject, htmlBody, null, null);
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody, String imgName, ClassPathResource img) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            if (img != null)
                helper.addInline(imgName, img);

            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void sendMailRequestToOwner(String to, Map<String, String> values, long ownerId) {

        Context thymeleafContext = getThymeleafContext(values, baseUrl + "/user/my-requests");
        thymeleafContext.setVariable("requestMessage", values.get("requestMessage"));
        String htmlBody = thymeleafTemplateEngine.process("owner-rent-request.html", thymeleafContext);

        sendHtmlMessage(to, "Nueva solicitud: " + values.get("articleName"), htmlBody, resourceName, LOGO);
    }

    @Override
    public void sendMailRequestToRenter(String to, Map<String, String> values) {

        Context thymeleafContext = getThymeleafContext(values, baseUrl + "/");
        String htmlBody = thymeleafTemplateEngine.process("renter-rent-request.html", thymeleafContext);

        sendHtmlMessage(to, "Solicitud enviada: " + values.get("articleName"), htmlBody, resourceName, LOGO);
    }

    @Override
    public void sendNewUserMail(String to, Map<String, String> values) {
        Context thymeleafContext = new Context();


        String htmlBody = thymeleafTemplateEngine.process("renter-rent-request.html", thymeleafContext);

        sendHtmlMessage(to, "Solicitud enviada: " + values.get("articleName"), htmlBody);
    }

    @Override
    public void sendMailRequestConfirmationToOwner(String to, Map<String, String> values, long ownerId) {
        Context thymeleafContext = getThymeleafContext(values, baseUrl + "/user/my-requests");
        String htmlBody = thymeleafTemplateEngine.process("owner-request-accepted.html", thymeleafContext);

        sendHtmlMessage(to, "Alquiler confirmado: " + values.get("articleName"), htmlBody, resourceName, LOGO);
    }

    @Override
    public void sendMailRequestConfirmationToRenter(String to, Map<String, String> values) {
        Context thymeleafContext = getThymeleafContext(values, baseUrl + "/");    //TODO: ver a donde mandar
        String htmlBody = thymeleafTemplateEngine.process("renter-request-accepted.html", thymeleafContext);
        sendHtmlMessage(to, "Alquiler confirmado: " + values.get("articleName"), htmlBody, resourceName, LOGO);
    }

    @Override
    public void sendMailRequestDenied(String to, Map<String, String> values) {
        Context thymeleafContext = getThymeleafContext(values, "/"); //me lleva al marketplace
        String htmlBody = thymeleafTemplateEngine.process("renter-request-denied.html", thymeleafContext);
        sendHtmlMessage(to, "Solicitud rechazada: " + values.get("articleName"), htmlBody, resourceName, LOGO);
    }

    private Context getThymeleafContext(Map<String, String> values, String callbackUrl) {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("renterName", values.get("renterName"));
        thymeleafContext.setVariable("ownerName", values.get("ownerName"));
        thymeleafContext.setVariable("startDate", values.get("startDate"));
        thymeleafContext.setVariable("endDate", values.get("endDate"));
        thymeleafContext.setVariable("articleName", values.get("articleName"));
        thymeleafContext.setVariable("renterEmail", values.get("renterEmail"));
        thymeleafContext.setVariable("ownerEmail", values.get("ownerEmail"));
        thymeleafContext.setVariable("imgSrc", "cid:" + resourceName);
        thymeleafContext.setVariable("callbackUrl", callbackUrl); // deberia ir a /user/{userId}/my-account
        return thymeleafContext;
    }
}
