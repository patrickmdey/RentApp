package ar.edu.itba.paw;


import ar.edu.itba.paw.interfaces.ArticleDao;
import ar.edu.itba.paw.interfaces.ArticleService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ArticleServiceImpl;
import ar.edu.itba.paw.services.UserServiceImpl;
import org.apache.commons.logging.Log;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {

    @InjectMocks
    private ArticleServiceImpl articleService = new ArticleServiceImpl();

    @Mock
    private ArticleDao mockDao;

    @Test
    public void testCreate() {

        final String title = "";
        final String description = "";
        final Float pricePerDay = Float.valueOf(123);
        final long idOwner = Long.valueOf(2);
        final List<Category> categories = new ArrayList<>();
        categories.add(new Category(1,"Tourism"));
        categories.add(new Category(2,"Kitchen"));


        Mockito.when(mockDao.createArticle(Mockito.eq(title), Mockito.eq(description),
                        Mockito.eq(pricePerDay), Mockito.eq(idOwner)))
                .thenReturn(Optional.of(new Article(1L, title, description, pricePerDay, categories, idOwner)));

        Optional<Article> optArticle = articleService.createArticle(title,description,pricePerDay,categories,idOwner);

        Assert.assertTrue(optArticle.isPresent());
        Article article = optArticle.get();

        Assert.assertEquals(title,article.getTitle());
        Assert.assertEquals(description,article.getDescription());
        Assert.assertEquals(pricePerDay,article.getPricePerDay());
        Assert.assertEquals(idOwner,article.getIdOwner());
        Assert.assertEquals(categories, article.getCategories());
    }

}
