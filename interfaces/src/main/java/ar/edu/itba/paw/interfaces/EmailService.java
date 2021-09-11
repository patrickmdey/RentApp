package ar.edu.itba.paw.interfaces;


import java.util.List;

public interface EmailService {

    void sendMessage(List<String> recipients, String title, String body);

    void sendMessage(String recipient, String title, String body);

}
