package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.RentState;
import ar.edu.itba.paw.models.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface RentService {
    Optional<RentProposal> findById(long id);

    List<RentProposal> ownerRequests(long ownerId, int state, Long limit, long page);

    List<RentProposal> sentRequests(long renterId, int state, Long limit, long page);

    void setRequestState(long requestId, RentState state, String webpageUrl, Locale locale);

    RentProposal create(String comment, LocalDate startDate, LocalDate endDate, long articleId,
                        long renterId, String webpageUrl, Locale locale);

    boolean hasRented(User renter, long articleId);

    long getReceivedMaxPage(long ownerId, int state, Long limit);

    long getSentMaxPage(long renterId, int state, Long limit);

    boolean isPresentSameDate(long renterId, long articleId, LocalDate startDate, LocalDate endDate);
}
