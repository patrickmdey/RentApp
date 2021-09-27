package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.RentProposal;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RentDao {

    List<RentProposal> list(long ownerId, int state, long page);

    Optional<RentProposal> findById(long id);

    void updateRequest(long requestId, int state);

    boolean hasRented(long renterId, long articleId);

    Optional<RentProposal> create(String comment, Integer approved, Date startDate, Date endDate, Long articleId, long renterId);

    Long getMaxPage(long ownerId, int state);

}
