package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.RequestsGetter;
import ar.edu.itba.paw.interfaces.dao.RentDao;
import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.EmailService;
import ar.edu.itba.paw.interfaces.service.RentService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.RentState;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.models.exceptions.RentProposalNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        proposal.setRenter(userService.findById(proposal.getRenterId()).orElseThrow(UserNotFoundException::new));
    }

    private void appendArticle(RentProposal proposal) {
        proposal.setArticle(articleService.findById((int) proposal.getArticleId()).orElseThrow(ArticleNotFoundException::new));
    }

    @Override
    public Optional<RentProposal> findById(long id) {
        return rentDao.findById(id);
    }

    @Override
    @Transactional
    public RentProposal create(String message, Integer approved, Date startDate,
                                         Date endDate, Long articleId, String renterName,
                                         String renterEmail, long renterId) {
        Article article = articleService.findById(articleId).orElseThrow(ArticleNotFoundException::new);

        User owner = userService.findById(article.getIdOwner()).orElseThrow(UserNotFoundException::new);
        RentProposal proposal = rentDao.create(message, approved, startDate, endDate, articleId, renterId);

        appendArticle(proposal);
        appendRenter(proposal);
        emailService.sendMailRequest(proposal, owner);
        return proposal;
    }

    @Override
    @Transactional
    public void acceptRequest(long requestId) {
        RentProposal rentProposal = rentDao.findById(requestId).orElseThrow(RentProposalNotFoundException::new);

        rentDao.updateRequest(requestId, RentState.ACCEPTED.ordinal());
        appendArticle(rentProposal);
        appendRenter(rentProposal);
        User owner = userService.findById(rentProposal.getArticle().getIdOwner()).orElseThrow(UserNotFoundException::new);

        emailService.sendMailRequestConfirmation(rentProposal, owner);
    }

    @Override
    @Transactional
    public void rejectRequest(long requestId) {
        RentProposal request = rentDao.findById(requestId).orElseThrow(RentProposalNotFoundException::new);

        rentDao.updateRequest(requestId, RentState.DECLINED.ordinal());
        appendArticle(request);
        appendRenter(request);
        User owner = userService.findById(request.getArticle().getIdOwner()).orElseThrow(UserNotFoundException::new);

        emailService.sendMailRequestDenied(request, owner);
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
