package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.RentState;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RentServiceImpl implements RentService {

    @Autowired
    private RentDao rentDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");


    private static final String baseUrl = "http://localhost:8080";
//    private static final String baseUrl = "http://pawserver.it.itba.edu.ar/paw-2021b-3";


    private List<RentProposal> getRequests(RequestsGetter getter, long accountId, int state, long page) {
        List<RentProposal> proposals = getter.get(accountId, state, page);
        proposals.forEach(proposal -> {
            appendArticle(proposal);
            appendRenter(proposal);
        });
        return proposals;
    }

    @Override
    public List<RentProposal> ownerRequests(long ownerId, int state, long page) {
        return getRequests(rentDao::ownerRequests, ownerId, state, page);
    }

    @Override
    public List<RentProposal> sentRequests(long ownerId, int state, long page) {
        return getRequests(rentDao::sentRequests, ownerId, state, page);
    }

    @Override
    public Long getReceivedMaxPage(long ownerId, int state) {
        return rentDao.getReceivedMaxPage(ownerId, state);
    }

    @Override
    public Long getSentMaxPage(long ownerId, int state) {
        return rentDao.getSentMaxPage(ownerId, state);
    }

    private void appendRenter(RentProposal proposal) {
        proposal.setRenter(userService.findById(proposal.getRenterId()).orElseThrow(RuntimeException::new));
    }

    private void appendArticle(RentProposal proposal) {
        proposal.setArticle(articleService.findById((int) proposal.getArticleId()).orElseThrow(RuntimeException::new));
    }


    @Override
    public Optional<RentProposal> findById(long id) {
        return rentDao.findById(id);
    }

    @Override
    @Transactional
    public Optional<RentProposal> create(String message, Integer approved, Date startDate,
                                         Date endDate, Long articleId, String renterName,
                                         String renterEmail, long renterId) {
        Optional<Article> article = articleService.findById(articleId);
        // TODO: Shouldn't we throw an exception here instead of isPresent() ?
        if (article.isPresent()) {
            Optional<User> owner = userService.findById(article.get().getIdOwner());

            Optional<RentProposal> proposal = rentDao.create(message, approved, startDate, endDate, articleId, renterId);
            if (proposal.isPresent()) {

                Map<String, String> values = new HashMap<>();

                if (owner.isPresent()) {
                    values.put("ownerName", owner.get().getFirstName());
                    values.put("renterName", renterName);
                    values.put("startDate", dateFormatter.format(startDate));
                    values.put("endDate", dateFormatter.format(endDate));
                    values.put("articleName", article.get().getTitle());
                    values.put("requestMessage", message);
                    values.put("callbackUrl", "http://localhost:8080/webapp_war/"); //TODO: HARCODEADO

                    emailService.sendMailRequestToOwner(owner.get().getEmail(), values, owner.get().getId());
                    emailService.sendMailRequestToRenter(renterEmail, values);

                    return proposal;
                }
            }
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void acceptRequest(long requestId) {
        RentProposal rentProposal = rentDao.findById(requestId).orElseThrow(RuntimeException::new); // TODO: RentProposalNotFoundException

        rentDao.updateRequest(requestId, RentState.ACCEPTED.ordinal());

        Map<String, String> values = getValuesMap(requestId);

        appendArticle(rentProposal);

        String category = String.valueOf(rentProposal.getArticle().getCategories().stream().findFirst().get().getId());

        values.put("articleCategory", category);

        emailService.sendMailRequestConfirmationToRenter(values.get("renterEmail"), values);
        emailService.sendMailRequestConfirmationToOwner(values.get("ownerEmail"), values, Long.parseLong(values.get("ownerId")));
    }

    @Override
    @Transactional
    public void rejectRequest(long requestId) {
        Map<String, String> values = getValuesMap(requestId);

        rentDao.updateRequest(requestId, RentState.DECLINED.ordinal());

        emailService.sendMailRequestDenied(values.get("renterEmail"), values);
    }

    private Map<String, String> getValuesMap(long requestId) {
        RentProposal request = rentDao.findById(requestId).orElseThrow(RuntimeException::new);

        User renter = userService.findById(request.getRenterId()).orElseThrow(RuntimeException::new);
        Article article = articleService.findById(request.getArticleId()).orElseThrow(RuntimeException::new);
        User owner = userService.findById(article.getIdOwner()).orElseThrow(RuntimeException::new);

        Map<String, String> values = new HashMap<>();

        values.put("renterName", renter.getFirstName());
        values.put("ownerName", owner.getFirstName());
        values.put("ownerId", String.valueOf(owner.getId()));
        values.put("startDate", dateFormatter.format(request.getStartDate()));
        values.put("endDate", dateFormatter.format(request.getEndDate()));
        values.put("articleName", article.getTitle());
        values.put("renterEmail", renter.getEmail());
        values.put("ownerEmail", owner.getEmail());
        values.put("callbackUrlOwner", baseUrl + "/user/" +
                values.get("ownerId") + "/my-account");
        values.put("callbackUrlRenter", "/");
        return values;
    }

    @Override
    public boolean hasRented(User renter, Long articleId) {
        if (articleId == null || renter == null)
            return false;

        return rentDao.hasRented(renter.getId(), articleId);
    }

    @Override
    public Boolean isPresentSameDate(long renterId, long articleId, Date startDate, Date endDate) {
        return rentDao.isPresentSameDate(renterId, articleId, startDate, endDate);
    }
}
