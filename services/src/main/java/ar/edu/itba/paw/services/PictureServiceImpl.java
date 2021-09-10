package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.PictureDao;
import ar.edu.itba.paw.interfaces.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureDao pictureDao;

    @Override
    public boolean addPictureToArticle(long pictureId, long articleId) {
        return pictureDao.addPictureToArticle(pictureId, articleId);
    }

    @Override
    public List<Long> listArticlePicturesUrl(long articleId) {
        return pictureDao.listArticlePicturesUrl(articleId);
    }
}
