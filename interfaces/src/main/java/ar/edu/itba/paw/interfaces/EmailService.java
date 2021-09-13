package ar.edu.itba.paw.interfaces;


import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import java.util.List;
import java.util.Map;

public interface EmailService {

    void sendMessageUsingThymeleafTemplate(String to, String subject);

    void sendMessage(List<String> recipients, String title, String body);

    void sendMessage(String recipient, String title, String body);

    void sendMailRequestToOwner(String to, Map<String, String> values);

    void sendMailRequestToRenter(String to, Map<String, String> values);

}
