package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ArticleDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.CannotCreateArticleException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ArticleDaoJpa implements ArticleDao {

    private static final long RESULTS_PER_PAGE = 9L;
    private static final long RECOMMENDED_AMOUNT = 4L;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Article> rentedArticles(long renterId, long page, Long limit) {
        OrderOptions orderOptions = OrderOptions.HIGHER_ARTICLE;
        String idsStringQuery = "SELECT id FROM article AS a WHERE id IN " + "(SELECT article_id FROM rent_proposal " +
                "WHERE renter_id = :renter_id AND state = :state) ORDER BY " +
                orderOptions.getNativeColumn() + " " + orderOptions.getOrder() + " LIMIT :limit OFFSET :offset";

        Query query = em.createNativeQuery(idsStringQuery);

        long resultsPerPage = limit == null ? RESULTS_PER_PAGE : limit;
        query.setParameter("renter_id", renterId);
        query.setParameter("state", RentState.ACCEPTED.ordinal());
        query.setParameter("limit", resultsPerPage);
        query.setParameter("offset", (page - 1) * resultsPerPage);

        @SuppressWarnings("unchecked")
        List<Long> rentedArticlesIds = ((List<Number>) query.getResultList()).stream().mapToLong(Number::longValue).boxed().collect(Collectors.toList());
        if (rentedArticlesIds.isEmpty())
            return Collections.emptyList();

        TypedQuery<Article> rentedArticleQuery = em.createQuery("FROM Article WHERE id IN (:rentedArticleIds) ORDER BY " + orderOptions.getJpaColumn() + " " + orderOptions.getOrder(), Article.class);

        rentedArticleQuery.setParameter("rentedArticleIds", rentedArticlesIds);

        return rentedArticleQuery.getResultList();
    }

    @Override
    public Optional<Article> findById(long id) {
        return Optional.ofNullable(em.find(Article.class, id));
    }

    @Override
    public Article createArticle(String title, String description, float pricePerDay, long idOwner) {
        User owner = em.find(User.class, idOwner);
        if (owner == null)
            throw new UserNotFoundException();

        Article article = new Article(title, description, pricePerDay, owner);
        try {
            em.persist(article);
            return article;
        } catch(Exception e) {
            throw new CannotCreateArticleException();
        }
    }


    private String parseNameQuery(String query) {
        StringBuilder toReturn = new StringBuilder();
        for (int i = 0; i < query.length(); i++) {
            char curr = query.charAt(i);
            if (curr == '%' || curr == '_')
                toReturn.append('\\');
            toReturn.append(curr);
        }
        return toReturn.toString();
    }

    private StringBuilder queryBuilder(Map<String, Object> params, String fields, String name, Long category, Long user, Long location, Float initPrice, Float endPrice) {
        StringBuilder query = new StringBuilder("SELECT " + fields + " FROM article AS a WHERE true ");

        if (name != null && name.length() > 0) {
            query.append(" AND LOWER(a.title) LIKE :title ");
            params.put("title", "%" + parseNameQuery(name.toLowerCase()) + "%");
        }

        if (user != null) {
            query.append(" AND owner_id = :owner_id ");
            params.put("owner_id", user);
        }

        if (category != null) {
            query.append(" AND a.id IN (SELECT article_id FROM article_category " +
                    "WHERE category_id = :category_id) ");
            params.put("category_id", category);
        }

        if (location != null) {
            query.append(" AND owner_id IN (SELECT account.id FROM account " +
                    "WHERE account.location = :location) ");
            params.put("location", location);
        }

        if (initPrice != null) {
            query.append(" AND price_per_day >= :initPrice ");
            params.put("initPrice", initPrice);
        }

        if (endPrice != null) {
            query.append(" AND price_per_day <= :endPrice ");
            params.put("endPrice", endPrice);
        }
        return query;
    }

    @Override
    public long getMaxPage(String name, Long category, Long user, Long location, Float initPrice, Float endPrice, Long limit) {
        Map<String, Object> params = new HashMap<>();
        Query query = em.createNativeQuery(queryBuilder(params, "COUNT(*)", name, category, user, location, initPrice, endPrice).toString());

        long resultsPerPage = limit == null ? RESULTS_PER_PAGE : limit;

        params.forEach(query::setParameter);

        long size = Long.parseLong(query.getSingleResult().toString());
        int toSum = (size % resultsPerPage == 0) ? 0 : 1;

        return (size / resultsPerPage) + toSum;
    }

    @Override
    public List<Article> filter(String name, Long category, OrderOptions orderBy, Long user, Long location, Float initPrice, Float endPrice, Long limit, long page) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder idQueryBuilder = queryBuilder(params, "id", name, category, user, location, initPrice, endPrice);
        idQueryBuilder.append(" ORDER BY ");

        idQueryBuilder.append(orderBy.getNativeColumn()).append(" ").append(orderBy.getOrder());

        idQueryBuilder.append(" LIMIT :limit OFFSET :offset");

        Query idQueries = em.createNativeQuery(idQueryBuilder.toString());
        params.forEach(idQueries::setParameter);

        long resultsPerPage = limit == null ? RESULTS_PER_PAGE : limit;
        idQueries.setParameter("limit", resultsPerPage);
        idQueries.setParameter("offset", (page - 1) * resultsPerPage);

        @SuppressWarnings("unchecked")
        List<Long> articleIds = ((List<Number>) idQueries.getResultList()).stream().mapToLong(Number::longValue).boxed().collect(Collectors.toList());

        if(articleIds.isEmpty())
            return Collections.emptyList();

        String hqlQuery = "SELECT a, " + orderBy.getJpaColumn() + " AS order_option " +
                "from Article AS a WHERE a.id IN (:articleIds) ORDER BY order_option " + orderBy.getOrder();

        final TypedQuery<Object[]> articleQuery = em.createQuery(hqlQuery, Object[].class);

        articleQuery.setParameter("articleIds", articleIds);

        return articleQuery.getResultList().stream().map(el -> (Article) el[0]).collect(Collectors.toList());
    }

    @Override
    public long getRentedMaxPage(long user, Long limit) {
        Query query = em.createNativeQuery("SELECT COUNT(*) FROM article WHERE id IN (" +
                "SELECT article_id FROM rent_proposal WHERE renter_id = :renter_id AND state = :state )");

        query.setParameter("renter_id", user);
        query.setParameter("state", RentState.ACCEPTED.ordinal());
        long size = Long.parseLong(query.getSingleResult().toString());

        long resultsPerPage = limit == null ? RESULTS_PER_PAGE : limit;
        int toSum = (size % resultsPerPage == 0) ? 0 : 1;
        return (size / resultsPerPage) + toSum;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Article> recommendedArticles(long articleId) {
        Query idQuery = em.createNativeQuery("SELECT id FROM article AS a1 WHERE a1.id != :article_id AND a1.id IN (SELECT a2.id " +
                "FROM article AS a2 JOIN rent_proposal rp1 ON a2.id = rp1.article_id " +
                "JOIN account acc on acc.id = rp1.renter_id WHERE acc.id IN " +
                "(SELECT acc2.id FROM account AS acc2 JOIN rent_proposal rp ON acc2.id = rp.renter_id" +
                " WHERE rp.article_id = :article_id)" +
                " GROUP BY a2.id " +
                " HAVING COUNT(DISTINCT rp1.renter_id) > 1) LIMIT " + RECOMMENDED_AMOUNT );

        idQuery.setParameter("article_id", articleId);

        @SuppressWarnings("unchecked")
        List<Long> articleIds = ((List<Number>) idQuery.getResultList()).stream().mapToLong(Number::longValue).boxed().collect(Collectors.toList());

        if(articleIds.isEmpty())
            return Collections.emptyList();

        String hqlQuery = "SELECT a from Article AS a WHERE a.id IN (:articleIds)";

        final TypedQuery<Article> articleQuery = em.createQuery(hqlQuery, Article.class);

        articleQuery.setParameter("articleIds", articleIds);

        return articleQuery.getResultList();
    }

    @Override
    public List<Locations> getUsedLocations() {
        TypedQuery<Locations> query = em.createQuery("SELECT DISTINCT a.owner.location FROM Article AS a", Locations.class);
        return query.getResultList();
    }
}
