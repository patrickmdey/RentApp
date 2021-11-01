package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RentService {
    Optional<RentProposal> findById(long id);

    List<RentProposal> ownerRequests(long ownerId, int state, long page);

    List<RentProposal> sentRequests(long renterId, int state, long page);

    void acceptRequest(long requestId);

    void rejectRequest(long requestId);

    RentProposal create(String comment, Integer approved, LocalDate startDate,
                        LocalDate endDate, Long articleId,
                                  String renterName, String renterEmail, long renterId);

    boolean hasRented(User renter, Long articleId);

    Long getReceivedMaxPage(long ownerId, int state);

    Long getSentMaxPage(long renterId, int state);

    Boolean isPresentSameDate(long renterId, long articleId, LocalDate startDate, LocalDate endDate);
}
