package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.RentProposal;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RentService {
    List<RentProposal> list();

    Optional<RentProposal> findById(long id);

    List<RentProposal> ownerRequests(long ownerId);

    Optional<RentProposal> create(String comment, Boolean approved, Date startDate,
                                  Date endDate, Integer idArticle,
                                  String renterName, String renterEmail, Integer idRenter);
}
