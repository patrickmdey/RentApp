package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.ArticleCategoryDao;
import ar.edu.itba.paw.interfaces.ArticleDao;
import ar.edu.itba.paw.interfaces.ArticleImageDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
import ar.edu.itba.paw.services.ArticleServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceImplTest {

    @InjectMocks
    private ArticleServiceImpl articleService = new ArticleServiceImpl();

    @Mock
    private ArticleDao articleDao;
    @Mock
    private ArticleCategoryDao articleCategoryDao;
    @Mock
    private ArticleImageDao articleImageDao;
    @Mock
    private UserDao userDao;


    private User userOwner;
    private User userRenter;
    private List<Article> articles;
    private List<Category> categories;
    private List<Long> categoriesId;

    @Before
    public void setUp(){
        this.userOwner = new User(1,"owner@mail.com","password","owner","owner",3L,null,UserType.OWNER.ordinal());
        this.userRenter = new User(2,"renter@mail.com","password","renter","renter",5L,null,UserType.RENTER.ordinal());

        this.articles = Arrays.asList(
                new Article(1, "Bicicleta","bmx de ciudad", 500F, 1),
                new Article(2, "Moto","Moto de campo", 900F, 1),
                new Article(3, "Auto","Auto de glaciar", 200F, 1),
                new Article(4, "Monopatin","Monopatin de CABA", 100F, 1)
        );

        this.categories = Arrays.asList(
                new Category(1,"Turismo"),
                new Category(2, "Automotor"),
                new Category(3, "Vacaciones"),
                new Category(4, "Ciudad")
        );

        this.categoriesId = categories.stream().map(Category::getId).collect(Collectors.toList());


    }

    @Test
    public void testCreate() {

        // Arrange
        Article articleToCreate = articles.stream().findFirst().get();

        when(articleDao.createArticle(
                eq(articleToCreate.getTitle()),
                eq(articleToCreate.getDescription()),
                eq(articleToCreate.getPricePerDay()),
                eq(articleToCreate.getIdOwner())
        )).thenReturn(Optional.of(articleToCreate));

        categoriesId.forEach(t-> {
            when(articleCategoryDao.addToArticle(
                    eq(articleToCreate.getId()),
                    eq(t)
            )).thenReturn(t);
        });

        when(articleCategoryDao.findFromArticle(articleToCreate.getId()))
                .thenReturn(categories);

        // Act

        Optional<Article> optionalArticle = articleService.createArticle(
                                                            articleToCreate.getTitle(),
                                                            articleToCreate.getDescription(),
                                                            articleToCreate.getPricePerDay(),
                                                            categoriesId,
                                                            new ArrayList<>(),
                                                            articleToCreate.getIdOwner()
        );

        // Assert

        Assert.assertTrue(optionalArticle.isPresent());

        Article article = optionalArticle.get();

        Assert.assertEquals(articleToCreate.getTitle(), article.getTitle());
        Assert.assertEquals(articleToCreate.getDescription(), article.getDescription());
        Assert.assertEquals(articleToCreate.getPricePerDay(), article.getPricePerDay());
        Assert.assertEquals(categories, article.getCategories());

    }

    @Test(expected = RuntimeException.class)
    public void testCreateFails() {

        // Arrange
        Article articleToCreate = articles.stream().findFirst().get();

        when(articleDao.createArticle(
                eq(articleToCreate.getTitle()),
                eq(articleToCreate.getDescription()),
                eq(articleToCreate.getPricePerDay()),
                eq(articleToCreate.getIdOwner())
        )).thenThrow(RuntimeException.class);
        // TODO: Look for what exception I should throw here


        // Act

        articleService.createArticle(
                articleToCreate.getTitle(),
                articleToCreate.getDescription(),
                articleToCreate.getPricePerDay(),
                categoriesId,
                new ArrayList<>(),
                articleToCreate.getIdOwner()
        );

        // Assert

        Assert.fail();

    }

    @Test
    public void testRentedArticles() {

        // Arrange
        when(articleImageDao.findFromArticle(anyLong()))
                .thenReturn(new ArrayList<>());

        when(userDao.findById(userOwner.getId()))
                .thenReturn(Optional.of(userOwner));

        when(articleDao.rentedArticles(eq(userRenter.getId()), anyLong()))
                .thenReturn(articles);


        // Act
        List<Article> results = articleService.rentedArticles(userRenter.getId(), 1);

        // Assert
        Assert.assertEquals(articles.size(), results.size());

        articles.forEach(t->{
            Optional<Article> optionalResult = results.stream().filter(r-> r.getId() == t.getId()).findFirst();
            Assert.assertTrue(optionalResult.isPresent());
            Article r = optionalResult.get();

            Assert.assertEquals((long)userOwner.getLocation(), r.getLocation().ordinal());
        });
    }

    @Test(expected = RuntimeException.class)
    public void testRentedArticlesFails() {

        // Arrange
        when(articleDao.rentedArticles(eq(userRenter.getId()), anyLong()))
                .thenThrow(RuntimeException.class);


        // Act
        List<Article> results = articleService.rentedArticles(userRenter.getId(), 1);

        // Assert

        Assert.fail();
    }

   @Test
    public void testEditArticle() {
        // Arrange
        Article articleToEdit = articles.get(0);

        articleToEdit.setTitle("new title");
        articleToEdit.setDescription("new description");
        articleToEdit.setPricePerDay(123F);
        articleToEdit.setCategories(Arrays.asList(
                new Category(123, "new category")
        ));

        final List<Long> newCategoriesId = articleToEdit.getCategories().stream().map(Category::getId).collect(Collectors.toList());

        when(articleDao.editArticle(
                eq(articleToEdit.getId()),
                eq(articleToEdit.getTitle()),
                eq(articleToEdit.getDescription()),
                eq(articleToEdit.getPricePerDay())
        )).thenReturn((int) articleToEdit.getId());

        when(articleCategoryDao.addToArticle(anyLong(),anyLong())).thenReturn(0L);
        doNothing().when(articleCategoryDao).removeFromArticle(anyLong(),anyLong());

        when(articleCategoryDao.findFromArticle(eq(articleToEdit.getId())))
                .thenReturn(categories);

        when(articleDao.findById(eq(articleToEdit.getId())))
                .thenReturn(Optional.of(articleToEdit));

        // Act
        Optional<Article> optionalArticle = articleService.editArticle(
                                                                    articleToEdit.getId(),
                                                                    articleToEdit.getTitle(),
                                                                    articleToEdit.getDescription(),
                                                                    articleToEdit.getPricePerDay(),
                                                                    newCategoriesId
        );

        // Assert
        Assert.assertTrue(optionalArticle.isPresent());
        Article article = optionalArticle.get();

        Assert.assertEquals(articleToEdit.getTitle(), article.getTitle());
        Assert.assertEquals(articleToEdit.getDescription(), article.getDescription());
        Assert.assertEquals(articleToEdit.getPricePerDay(), article.getPricePerDay());

   }


    @Test(expected = RuntimeException.class)
    public void testEditArticleFails() {
        // Arrange
        Article articleToEdit = articles.get(0);

        final List<Long> newCategoriesId = articleToEdit.getCategories().stream().map(Category::getId).collect(Collectors.toList());

        when(articleDao.editArticle(
                eq(articleToEdit.getId()),
                eq(articleToEdit.getTitle()),
                eq(articleToEdit.getDescription()),
                eq(articleToEdit.getPricePerDay())
        )).thenThrow(RuntimeException.class);

        // Act
        Optional<Article> optionalArticle = articleService.editArticle(
                articleToEdit.getId(),
                articleToEdit.getTitle(),
                articleToEdit.getDescription(),
                articleToEdit.getPricePerDay(),
                newCategoriesId
        );

        // Assert
        Assert.fail();

    }







}
