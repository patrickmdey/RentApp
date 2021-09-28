package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.RentState;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public List<RentProposal> ownerRequests(long ownerId, int state, long page) {

        List<RentProposal> proposals = rentDao.list(ownerId, state, page);
        proposals.forEach(proposal -> {
            appendArticle(proposal);
            appendRenter(proposal);
        });
        return proposals;
    }

    @Override
    public Long getMaxPage(long ownerId, int state) {
        return rentDao.getMaxPage(ownerId, state);
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
    public Optional<RentProposal> create(String message, Integer approved, Date startDate,
                                         Date endDate, Long articleId, String renterName,
                                         String renterEmail, long renterId) {
        Optional<Article> article = articleService.findById(articleId);
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
                    values.put("callbackUrl", "http://localhost:8080/webapp_war/"); //HARCODEADO

                    emailService.sendMailRequestToOwner(owner.get().getEmail(), values, owner.get().getId());

                    emailService.sendMailRequestToRenter(renterEmail, values);

                    return proposal;
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public void acceptRequest(long requestId) {

        rentDao.updateRequest(requestId, RentState.ACCEPTED.ordinal());

        Map<String, String> values = getValuesMap(requestId);

        emailService.sendMailRequestConfirmationToRenter(values.get("renterEmail"), values);
        emailService.sendMailRequestConfirmationToOwner(values.get("ownerEmail"), values, Long.parseLong(values.get("ownerId")));
    }

    @Override
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
        values.put("callbackUrlOwner", "http://localhost:8080/user/" +
                values.get("ownerId") + "/my-account"); // deberia ir a /user/{userId}/my-account
        values.put("callbackUrlRenter", "/");
        return values;
    }

    @Override
    public boolean hasRented(User renter, Long articleId) {
        if (articleId == null || renter == null)
            return false;

        return rentDao.hasRented(renter.getId(), articleId);
    }
}
