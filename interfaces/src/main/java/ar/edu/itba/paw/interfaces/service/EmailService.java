package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.User;
import java.util.Locale;

public interface EmailService {

    void sendNewUserMail(User newUser, String webpageUrl, Locale locale);

    void sendMailRequest(RentProposal rentProposal, User owner, String webpageUrl, Locale locale);

    void sendMailRequestConfirmation(RentProposal rentProposal, User owner, String webpageUrl, Locale locale);

    void sendMailRequestDenied(RentProposal rentProposal, User owner, String webpageUrl, Locale locale);
}
