package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.ImageDao;
import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private ArticleService articleService;

    @Override
    public List<DBImage> listFromArticle(int articleId) {
        return articleService.findById(articleId).orElseThrow(ArticleNotFoundException::new).getImages();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DBImage> findById(long id) {
        return imageDao.findById(id);
    }

    @Override
    @Transactional
    public DBImage create(byte[] img) {
        return imageDao.create(img);
    }
}
