package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.RentProposal;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RentDao {

    List<RentProposal> list();

    Optional<RentProposal> findById(long id);


    Optional<RentProposal> create(String comment, Boolean approved, Date startDate, Date endDate, Integer idArticle, Integer idRenter);
}
