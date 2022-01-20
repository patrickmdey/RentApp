package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.models.DBImage;

import java.util.Optional;

public interface ImageService {

    Optional<DBImage> findById(long id);

    DBImage create(byte[] img);
}
