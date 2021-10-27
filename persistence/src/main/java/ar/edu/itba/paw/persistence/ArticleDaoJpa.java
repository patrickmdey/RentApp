package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ArticleDao;
import ar.edu.itba.paw.models.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ArticleDaoJpa implements ArticleDao {

    private static final Long RESULTS_PER_PAGE = 4L;

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<Article> rentedArticles(long renterId, long page) {
        Query query = em.createNativeQuery("SELECT id FROM article AS a WHERE id IN (" +
                "SELECT article_id FROM rent_proposal WHERE renter_id = :renter_id AND state = :state ORDER BY start_date)" +
                "LIMIT :limit OFFSET :offset");

        query.setParameter("renter_id", renterId);
        query.setParameter("state", RentState.ACCEPTED.ordinal());
        query.setParameter("limit", RESULTS_PER_PAGE);
        query.setParameter("offset", page * RESULTS_PER_PAGE);

        List<Integer> aux = query.getResultList();

        List<Long> rentedArticlesIds = aux.stream().mapToLong(Integer::longValue).boxed().collect(Collectors.toList());

        if (rentedArticlesIds.isEmpty())
            return new ArrayList<>();

        TypedQuery<Article> rentedArticleQuery = em.createQuery("FROM Article WHERE" +
                "id IN (:rentedArticleIds) ORDER BY start_date", Article.class);

        rentedArticleQuery.setParameter("rentedArticleIds", rentedArticlesIds);

        return rentedArticleQuery.getResultList();
    }

    @Override
    public Optional<Article> findById(long id) {
        return Optional.ofNullable(em.find(Article.class, id));
    }

    @Override
    public Article createArticle(String title, String description, Float pricePerDay, long idOwner) {
        User owner = em.find(User.class, idOwner);
        Article article = new Article(title, description, pricePerDay, owner);
        em.persist(article);
        return article;
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

    private StringBuilder queryBuilder(Map<String, Object> params, String fields, String name, Long category, Long user, Long location) {
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
        return query;
    }

    @Override
    public Long getMaxPage(String name, Long category, Long user, Long location) {
        Map<String, Object> params = new HashMap<>();
        Query query = em.createNativeQuery(queryBuilder(params, "COUNT(*)", name, category, user, location).toString());

        params.forEach(query::setParameter);

        long size = Long.parseLong(query.getSingleResult().toString());
        int toSum = (size % RESULTS_PER_PAGE == 0) ? 0 : 1;

        return (size / RESULTS_PER_PAGE) + toSum;
    }

    @Override
    public List<Article> filter(String name, Long category, OrderOptions orderBy, Long user, Long location, long page) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder idQueryBuilder = queryBuilder(params, "id", name, category, user, location);
        idQueryBuilder.append(" ORDER BY ");

        idQueryBuilder.append(orderBy.getNativeColumn()).append(" ").append(orderBy.getOrder());

        idQueryBuilder.append(" LIMIT :limit OFFSET :offset");

        Query idQueries = em.createNativeQuery(idQueryBuilder.toString());
        params.forEach(idQueries::setParameter);

        idQueries.setParameter("limit", RESULTS_PER_PAGE);
        idQueries.setParameter("offset", (page - 1) * RESULTS_PER_PAGE);

        @SuppressWarnings("unchecked")
        List<Long> articleIds = ((List<Integer>) idQueries.getResultList()).stream().mapToLong(Integer::longValue).boxed().collect(Collectors.toList());

        if(articleIds.isEmpty())
            return new ArrayList<>();

        String hqlQuery = "SELECT a, " + orderBy.getJpaColumn() + " AS order_option " +
                "from Article AS a WHERE a.id IN (:articleIds) ORDER BY order_option " + orderBy.getOrder();

        final TypedQuery<Object[]> articleQuery = em.createQuery(hqlQuery, Object[].class);

        articleQuery.setParameter("articleIds", articleIds);

        return articleQuery.getResultList().stream().map(el -> (Article) el[0]).collect(Collectors.toList());
    }

    @Override
    public Long getRentedMaxPage(Long user) {
        Query query = em.createNativeQuery("SELECT COUNT(*) FROM article WHERE id IN (" +
                "SELECT article_id FROM rent_proposal WHERE renter_id = :renter_id AND state = :state )");

        query.setParameter("renter_id", user);
        query.setParameter("state", RentState.ACCEPTED.ordinal());
        long size = Long.parseLong(query.getSingleResult().toString());

        int toSum = (size % RESULTS_PER_PAGE == 0) ? 0 : 1;
        return (size / RESULTS_PER_PAGE) + toSum;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Article> recommendedArticles(long articleId) {
        Query query = em.createNativeQuery("SELECT * FROM article AS a1 WHERE a1.id != :article_id AND a1.id IN (SELECT a2.id " +
                "FROM article AS a2 JOIN rent_proposal rp1 ON a2.id = rp1.article_id " +
                "JOIN account acc on acc.id = rp1.renter_id WHERE acc.id IN " +
                "(SELECT acc2.id FROM account AS acc2 JOIN rent_proposal rp ON acc2.id = rp.renter_id" +
                " WHERE rp.article_id = :article_id)" +
                " GROUP BY a2.id " +
                " HAVING COUNT(DISTINCT rp1.renter_id) > 1" +
                ")");

        query.setParameter("article_id", articleId);

        return (List<Article>) query.getResultList();
    }

    @Override
    public List<Locations> getUsedLocations() {
        TypedQuery<Locations> query = em.createQuery("SELECT DISTINCT a.owner.location FROM Article AS a"
                , Locations.class);

        return query.getResultList();
    }
}
