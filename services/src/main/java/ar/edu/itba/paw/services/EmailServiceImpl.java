package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EmailService;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public void sendMessage(List<String> recipients, String title, String body) {

    }

    @Override
    public void sendMessage(String recipient, String title, String body) {
        Properties props = System.getProperties();
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "pyd.com.ar");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("paw_itba@pyd.com.ar","PAW_root_123");
            }
        });

        session.setDebug(true);

        try {

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("paw_itba@pyd.com.ar", false));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            msg.setSubject(title);
            msg.setText(body);
            msg.setSentDate(new Date());

            Transport.send(msg);
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
