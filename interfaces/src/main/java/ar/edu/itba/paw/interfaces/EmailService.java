package ar.edu.itba.paw.interfaces;


import java.util.Map;

public interface EmailService {

    void sendNewUserMail(String to, Map<String, String> values);

    void sendMailRequestToOwner(String to, Map<String, String> values, long ownerId);

    void sendMailRequestToRenter(String to, Map<String, String> values);

    void sendMailRequestConfirmationToOwner(String to, Map<String, String> values, long ownerId);

    void sendMailRequestConfirmationToRenter(String to, Map<String, String> values);

    void sendMailRequestDenied(String to, Map<String, String> values);

}
