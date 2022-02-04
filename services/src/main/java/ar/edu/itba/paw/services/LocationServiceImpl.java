package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.LocationService;
import ar.edu.itba.paw.models.Locations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private ArticleService articleService;

    @Override
    public List<Locations> list(boolean used) {
        if(used)
            return articleService.getUsedLocations();
        return Arrays.asList(Locations.values());
    }
}