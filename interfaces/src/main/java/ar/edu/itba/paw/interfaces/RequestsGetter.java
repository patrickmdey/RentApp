package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.RentProposal;

import java.util.List;

@FunctionalInterface
public interface RequestsGetter {
    List<RentProposal> get(long id, int state, long page);
}
