package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.service.EmailService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.UnableToSendEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    private static final ClassPathResource LOGO = new ClassPathResource("/image/rentapp-logo.png");

    private static final String baseUrl = "http://localhost:8080";
//    private static final String baseUrl = "http://pawserver.it.itba.edu.ar/paw-2021b-3";

    private static final String resourceName = "logo";

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Autowired
    private JavaMailSender emailSender;


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
            throw new UnableToSendEmailException();
        }
    }

    @Override
    public void sendMailRequest(RentProposal rentProposal, User owner) {
        Context thymeleafContext = getThymeleafContext(rentProposal, owner);
//        thymeleafContext.setVariable("callbackUrl", callbackUrl); // deberia ir a /user/{userId}/my-account
        sendMailRequestToOwner(thymeleafContext);
        sendMailRequestToRenter(thymeleafContext);
    }

    private void sendMailRequestToOwner(Context context) {
        context.setVariable("callbackUrl", baseUrl + "/user/my-requests");
        String htmlBody = thymeleafTemplateEngine.process("owner-rent-request.html", context);
        sendHtmlMessage((String) context.getVariable("ownerEmail"), "Nueva solicitud: "
                + context.getVariable("articleName"), htmlBody, resourceName, LOGO);
    }

    private void sendMailRequestToRenter(Context context) {
        context.setVariable("callbackUrl", baseUrl + "/");
        String htmlBody = thymeleafTemplateEngine.process("renter-rent-request.html", context);
        sendHtmlMessage((String) context.getVariable("renterEmail"), "Solicitud enviada: "
                + context.getVariable("articleName"), htmlBody, resourceName, LOGO);
    }

    @Override
    public void sendNewUserMail(User newUser) {
        Context context = new Context();
        context.setVariable("name", newUser.getFirstName());
        context.setVariable("email", newUser.getEmail());
        context.setVariable("callbackUrl", baseUrl + "/user/login");
        String htmlBody = thymeleafTemplateEngine.process("new-user.html", context);
        sendHtmlMessage(newUser.getEmail(), "Bienvenido a Rentapp", htmlBody);
    }

    @Override
    public void sendMailRequestConfirmation(RentProposal rentProposal, User owner) {
        Context thymeleafContext = getThymeleafContext(rentProposal, owner);
        sendMailRequestConfirmationToOwner(thymeleafContext);

        sendMailRequestConfirmationToRenter(thymeleafContext, rentProposal.getArticle().getCategories().get(0).getId());
    }

    private void sendMailRequestConfirmationToOwner(Context context) {
        context.setVariable("callBackUrl", baseUrl + "/user/my-requests");
        String htmlBody = thymeleafTemplateEngine.process("owner-request-accepted.html", context);
        sendHtmlMessage((String) context.getVariable("ownerEmail"), "Alquiler confirmado: " + context.getVariable("articleName"), htmlBody, resourceName, LOGO);
    }

    private void sendMailRequestConfirmationToRenter(Context context, long categoryId) {
        context.setVariable("callbackUrl", baseUrl + "/?category=" + categoryId);
        String htmlBody = thymeleafTemplateEngine.process("renter-request-accepted.html", context);
        sendHtmlMessage((String) context.getVariable("renterEmail"), "Alquiler confirmado: "
                + context.getVariable("articleName"), htmlBody, resourceName, LOGO);
    }

    @Override
    public void sendMailRequestDenied(RentProposal rentProposal, User owner) {
        Context thymeleafContext = getThymeleafContext(rentProposal, owner);
        long categoryId = rentProposal.getArticle().getCategories().get(0).getId();
        thymeleafContext.setVariable("callbackUrl", baseUrl + "/?category=" + categoryId);
        String htmlBody = thymeleafTemplateEngine.process("renter-request-denied.html", thymeleafContext);
        sendHtmlMessage((String) thymeleafContext.getVariable("renterEmail"), "Solicitud rechazada: "
                + thymeleafContext.getVariable("articleName"), htmlBody, resourceName, LOGO);
    }


    private Context getThymeleafContext(RentProposal rentProposal, User owner) {
        Context thymeleafContext = new Context();
        Article article = rentProposal.getArticle();
        User renter = rentProposal.getRenter();
        thymeleafContext.setVariable("renterName", renter.getFirstName());
        thymeleafContext.setVariable("ownerName", owner.getFirstName());
        thymeleafContext.setVariable("startDate", rentProposal.getStartDate());
        thymeleafContext.setVariable("endDate", rentProposal.getEndDate());
        thymeleafContext.setVariable("articleName", article.getTitle());
        thymeleafContext.setVariable("renterEmail", renter.getEmail());
        thymeleafContext.setVariable("ownerEmail", owner.getEmail());
        thymeleafContext.setVariable("requestMessage", rentProposal.getMessage());
        thymeleafContext.setVariable("imgSrc", "cid:" + resourceName);
        return thymeleafContext;
    }
}
