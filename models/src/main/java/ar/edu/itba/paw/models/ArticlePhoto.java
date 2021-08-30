package ar.edu.itba.paw.models;

import java.util.UUID;

public class ArticlePhoto {

    private UUID id;
    private Integer idArticle;


    public UUID getId() {
        return id;
    }

    public Integer getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Integer idArticle) {
        this.idArticle = idArticle;
    }

}
