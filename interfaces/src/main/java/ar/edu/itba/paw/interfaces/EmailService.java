package ar.edu.itba.paw.interfaces;


import java.util.List;
import java.util.Map;

public interface EmailService {

    void sendMessage(List<String> recipients, String title, String body);

    void sendNewUserMail(String to, Map<String, String> values);

    void sendMessage(String recipient, String title, String body);

    void sendMailRequestToOwner(String to, Map<String, String> values);

    void sendMailRequestToRenter(String to, Map<String, String> values);

}
