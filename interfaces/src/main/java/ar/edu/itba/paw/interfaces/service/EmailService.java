package ar.edu.itba.paw.interfaces.service;


import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.User;

public interface EmailService {

    void sendNewUserMail(User newUser, String webpageUrl);

    void sendMailRequest(RentProposal rentProposal, User owner, String webpageUrl);

    void sendMailRequestConfirmation(RentProposal rentProposal, User owner, String webpageUrl);

    void sendMailRequestDenied(RentProposal rentProposal, User owner, String webpageUrl);
}
