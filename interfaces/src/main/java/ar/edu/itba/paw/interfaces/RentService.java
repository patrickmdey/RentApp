package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RentService {
    Optional<RentProposal> findById(long id);

    List<RentProposal> ownerRequests(long ownerId, int state, long page);

    void acceptRequest(long requestId);

    void rejectRequest(long requestId);

    Optional<RentProposal> create(String comment, Integer approved, Date startDate,
                                  Date endDate, Long articleId,
                                  String renterName, String renterEmail, long renterId);

    boolean hasRented(User renter, Long articleId);

    Long getMaxPage(long ownerId, int state);

}
