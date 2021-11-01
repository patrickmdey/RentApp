package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.RentProposal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RentDao {

    List<RentProposal> ownerRequests(long ownerId, int state, long page);

    List<RentProposal> sentRequests(long renterId, int state, long page);

    Optional<RentProposal> findById(long id);

    boolean hasRented(long renterId, long articleId);

    RentProposal create(String comment, Integer approved, LocalDate startDate, LocalDate endDate, Long articleId, long renterId);

    Long getReceivedMaxPage(long ownerId, int state);

    Long getSentMaxPage(long renterId, int state);

    Boolean isPresentSameDate(long renterId, long articleId, LocalDate startDate, LocalDate endDate);
}
