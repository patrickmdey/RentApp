package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.RentProposal;
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

    @Override
    public List<RentProposal> ownerRequests(long ownerId) {

        List<RentProposal> proposals = rentDao.list(ownerId);
        proposals.forEach(proposal -> {
            appendArticle(proposal);
            appendRenter(proposal);
        });
        return proposals;
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
    public Optional<RentProposal> create(String message, Boolean approved, Date startDate,
                                         Date endDate, Integer articleId, String renterName,
                                         String renterEmail, long renterId) {
        Optional<Article> article = articleService.findById(articleId);
        if (article.isPresent()) {
            Optional<User> owner = userService.findById(article.get().getIdOwner());

            Optional<RentProposal> proposal = rentDao.create(message, approved, startDate, endDate, articleId, renterId);
            if (proposal.isPresent()) {

                Map<String, String> values = new HashMap<>();

                if (owner.isPresent()) {

                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

                    values.put("ownerName", owner.get().getFirstName());
                    values.put("renterName", renterName);
                    values.put("startDate", dateFormatter.format(startDate));
                    values.put("endDate", dateFormatter.format(endDate));
                    values.put("articleName", article.get().getTitle());
                    values.put("requestMessage", message);
                    values.put("callbackUrl", "http://localhost:8080/webapp_war/"); //HARCODEADO

                    emailService.sendMailRequestToOwner(owner.get().getEmail(), values);

                    emailService.sendMailRequestToRenter(renterEmail, values);

                    return proposal;
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public void acceptRequest(long requestId) {
        rentDao.acceptRequest(requestId);
    }

    @Override
    public void deleteRequest(long requestId) {
        rentDao.deleteRequest(requestId);
    }
}
