package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.DBImage;

import java.util.Optional;

public interface ImageDao {

    Optional<DBImage> findById(long id);

    Optional<DBImage> create(byte[] img);

}
