package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.ImageDao;
import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.exceptions.CannotCreateImageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Override
    @Transactional(readOnly = true)
    public Optional<DBImage> findById(long id) {
        return imageDao.findById(id);
    }

    @Override
    @Transactional
    public DBImage create(MultipartFile img) {
        try {
            byte[] data = img.getBytes();
            return imageDao.create(data);
        } catch (IOException e) {
            throw new CannotCreateImageException();
        }
    }
}
