package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ArticlePictureDao;
import ar.edu.itba.paw.interfaces.ArticlePictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticlePictureServiceImpl implements ArticlePictureService {

    @Autowired
    private ArticlePictureDao articlePictureDao;

    @Override
    public boolean addPictureToArticle(long pictureId, long articleId) {
        return articlePictureDao.addPictureToArticle(pictureId, articleId);
    }

    @Override
    public List<Long> listArticlePicturesUrl(long articleId) {
        return articlePictureDao.listArticlePicturesUrl(articleId);
    }
}
