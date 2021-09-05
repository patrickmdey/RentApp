package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.ArticleDao;
import ar.edu.itba.paw.persistence.ArticleDaoJdbc;

public class ArticlesDaoTest {
    @InjectMocks
    private ArticleDao articleDao = new ArticleDaoJdbc();
}
