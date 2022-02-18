package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.models.Locations;
import java.util.List;

public interface LocationService {
    List<Locations> list(boolean used);
}
