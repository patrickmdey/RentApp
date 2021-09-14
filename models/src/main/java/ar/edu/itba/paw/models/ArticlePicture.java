package ar.edu.itba.paw.models;

public class ArticlePicture {

    private long pictureId;

    private long articleId;

    public ArticlePicture(long pictureId, long articleId) {
        this.pictureId = pictureId;
        this.articleId = articleId;
    }

    public long getPictureId() {
        return pictureId;
    }

    public void setPictureId(long pictureId) {
        this.pictureId = pictureId;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }
}
