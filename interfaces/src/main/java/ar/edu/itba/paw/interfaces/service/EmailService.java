package ar.edu.itba.paw.interfaces.service;


import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.User;

public interface EmailService {

    void sendNewUserMail(User newUser);

    void sendMailRequest(RentProposal rentProposal, User owner);

//    void sendMailRequestConfirmationToOwner(String to, Map<String, String> values, long ownerId);

    void sendMailRequestConfirmation(RentProposal rentProposal, User owner);

    void sendMailRequestDenied(RentProposal rentProposal, User owner);
}
