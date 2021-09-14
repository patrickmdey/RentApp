package ar.edu.itba.paw.interfaces;

import java.util.List;

public interface PictureDao {

    public boolean addPictureToArticle(long pictureId, long articleId);

    public List<Long> listArticlePicturesUrl(long articleId);

}
