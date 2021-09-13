package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<RentProposal> list() {
        return rentDao.list();
    }

    @Override
    public Optional<RentProposal> findById(long id) {
        return rentDao.findById(id);
    }

    @Override
    public Optional<RentProposal> create(String message, Boolean approved, Date startDate, Date endDate, Integer idArticle, String renterName, Integer idRenter) {
        Optional<Article> article = articleService.findById(idArticle);
        if (article.isPresent()) {
            Optional<User> owner = userService.findById(article.get().getIdOwner());

            Optional<RentProposal> proposal = rentDao.create(message, approved, startDate, endDate, idArticle, idRenter);
            if (proposal.isPresent()) {

                Map<String, String> values = new HashMap<>();

                if (owner.isPresent()) {

                    values.put("ownerName", owner.get().getFirstName());
                    values.put("renterName", renterName);
                    values.put("startDate", startDate.toString());
                    values.put("endDate", endDate.toString());
                    values.put("articleName", article.get().getTitle());
                    values.put("requestMessage", message);
                    values.put("callbackUrl", "http://localhost:8080/webapp_war/"); //HARCODEADO

                    emailService.sendMailRequestToOwner(owner.get().getEmail(), values);
                }
            }
        }

        return Optional.empty();
    }
}
