package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.service.EmailService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.CategoryNotFoundException;
import ar.edu.itba.paw.models.exceptions.UnableToSendEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class EmailServiceImpl implements EmailService {
    private static final ClassPathResource LOGO = new ClassPathResource("/image/rentapp-logo.png");

    private static final String RESOURCE_NAME = "logo";

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private MessageSource emailMessageSource;

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
    public void sendMailRequest(RentProposal rentProposal, User owner, String webpageUrl, Locale locale) {
        Context thymeleafContext = getThymeleafContext(rentProposal, owner, locale);
        sendMailRequestToOwner(thymeleafContext, webpageUrl, locale);
        sendMailRequestToRenter(thymeleafContext, webpageUrl, locale);
    }

    private void sendMailRequestToOwner(Context context, String webpageUrl, Locale locale) {
        context.setVariable("callbackUrl", webpageUrl + "/proposals");
        String htmlBody = thymeleafTemplateEngine.process("owner-rent-request.html", context);
        sendHtmlMessage((String) context.getVariable("ownerEmail"),
                emailMessageSource.getMessage("email.newRequest.owner", null, locale)
                        + context.getVariable("articleName"), htmlBody, RESOURCE_NAME, LOGO);
    }

    private void sendMailRequestToRenter(Context context, String webpageUrl, Locale locale) {
        context.setVariable("callbackUrl", webpageUrl + "/marketplace");
        String htmlBody = thymeleafTemplateEngine.process("renter-rent-request.html", context);
        sendHtmlMessage((String) context.getVariable("renterEmail"),
                emailMessageSource.getMessage("email.newRequest.renter", null, locale)
                        + context.getVariable("articleName"), htmlBody, RESOURCE_NAME, LOGO);
    }

    @Override
    public void sendNewUserMail(User newUser, String webpageUrl, Locale locale) {
        Context context = new Context();
        context.setVariable("name", newUser.getFirstName());
        context.setVariable("email", newUser.getEmail());
        context.setVariable("callbackUrl", webpageUrl + "/login");
        String htmlBody = thymeleafTemplateEngine.process("new-user.html", context);
        sendHtmlMessage(newUser.getEmail(),
                emailMessageSource.getMessage("email.newUser.subject", null, locale)
                , htmlBody);
    }

    @Override
    public void sendMailRequestConfirmation(RentProposal rentProposal, User owner, String webpageUrl, Locale locale) {
        Context thymeleafContext = getThymeleafContext(rentProposal, owner, locale);
        sendMailRequestConfirmationToOwner(thymeleafContext, webpageUrl, locale);

        sendMailRequestConfirmationToRenter(thymeleafContext,
                rentProposal.getArticle().getCategories().stream().findFirst()
                        .orElseThrow(CategoryNotFoundException::new).getId(), webpageUrl, locale);
    }

    private void sendMailRequestConfirmationToOwner(Context context, String webpageUrl, Locale locale) {
        context.setVariable("callbackUrl", webpageUrl + "/proposals");
        String htmlBody = thymeleafTemplateEngine.process("owner-request-accepted.html", context);
        sendHtmlMessage((String) context.getVariable("ownerEmail"), emailMessageSource.getMessage("email.accepted.renter", null, locale) + context.getVariable("articleName"), htmlBody, RESOURCE_NAME, LOGO);
    }

    private void sendMailRequestConfirmationToRenter(Context context, long categoryId, String webpageUrl, Locale locale) {
        context.setVariable("callbackUrl", webpageUrl + "/marketplace?category=" + categoryId);
        String htmlBody = thymeleafTemplateEngine.process("renter-request-accepted.html", context);
        sendHtmlMessage((String) context.getVariable("renterEmail"), emailMessageSource.getMessage("email.accepted.renter", null, locale)
                + context.getVariable("articleName"), htmlBody, RESOURCE_NAME, LOGO);
    }

    @Override
    public void sendMailRequestDenied(RentProposal rentProposal, User renter, String webpageUrl, Locale locale) {
        Context thymeleafContext = getThymeleafContext(rentProposal, renter, locale);
        long categoryId = rentProposal.getArticle().getCategories().stream().findFirst().orElseThrow(CategoryNotFoundException::new).getId();
        thymeleafContext.setVariable("callbackUrl", webpageUrl + "/marketplace?category=" + categoryId);
        String htmlBody = thymeleafTemplateEngine.process("renter-request-denied.html", thymeleafContext);
        sendHtmlMessage((String) thymeleafContext.getVariable("renterEmail"), emailMessageSource.getMessage("email.deniedRequest", null, locale)
                + thymeleafContext.getVariable("articleName"), htmlBody, RESOURCE_NAME, LOGO);
    }


    private Context getThymeleafContext(RentProposal rentProposal, User owner, Locale locale) {
        Context thymeleafContext = new Context(locale);
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
        thymeleafContext.setVariable("imgSrc", "cid:" + RESOURCE_NAME);
        return thymeleafContext;
    }
}
