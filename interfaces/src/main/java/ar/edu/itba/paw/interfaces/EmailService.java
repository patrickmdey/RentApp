package ar.edu.itba.paw.interfaces;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.util.List;

public interface EmailService {

    void sendMessage(List<String> recipients, String title, String body) throws MessagingException;

    void sendMessage(String recipient, String title, String body) throws MessagingException;

}
