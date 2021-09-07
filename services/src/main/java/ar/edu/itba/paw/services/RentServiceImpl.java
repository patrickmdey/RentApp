package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public Optional<RentProposal> create(String comment, Boolean approved, Date startDate, Date endDate, Integer idArticle, Integer idRenter) {
        Optional<Article> article = articleService.findById(idArticle);
        if (article.isPresent()) {
            Optional<RentProposal> proposal = rentDao.create(comment, approved, startDate, endDate, idArticle, idRenter);
            if (proposal.isPresent()) {
                Optional<User> user = userService.findById(article.get().getIdOwner());
                user.ifPresent(value -> emailService.sendMessage(value.getEmail(),
                        "New Rent Proposal", "You have received a new rent proposal"));
            }
        }

        return Optional.empty();
    }
}
