package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.RequestsGetter;
import ar.edu.itba.paw.interfaces.dao.RentDao;
import ar.edu.itba.paw.interfaces.service.EmailService;
import ar.edu.itba.paw.interfaces.service.RentService;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.RentState;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.RentProposalNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@Service
public class RentServiceImpl implements RentService {

    @Autowired
    private RentDao rentDao;

    @Autowired
    private EmailService emailService;

    private List<RentProposal> getRequests(RequestsGetter getter, long accountId, int state, long page) {
        return getter.get(accountId, state, page);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentProposal> ownerRequests(long ownerId, int state, long page) {
        return getRequests(rentDao::ownerRequests, ownerId, state, page);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentProposal> sentRequests(long ownerId, int state, long page) {
        return getRequests(rentDao::sentRequests, ownerId, state, page);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getReceivedMaxPage(long ownerId, int state) {
        return rentDao.getReceivedMaxPage(ownerId, state);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getSentMaxPage(long ownerId, int state) {
        return rentDao.getSentMaxPage(ownerId, state);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RentProposal> findById(long id) {
        return rentDao.findById(id);
    }

    @Override
    @Transactional
    public RentProposal create(String message, LocalDate startDate, LocalDate endDate, long articleId,
                               long renterId, String webpageUrl) {
        RentProposal proposal = rentDao.create(message, RentState.PENDING.ordinal(), startDate, endDate, articleId, renterId);
        emailService.sendMailRequest(proposal, proposal.getArticle().getOwner(), webpageUrl);

        return proposal;
    }

    @Override
    @Transactional
    public void acceptRequest(long requestId, String webpageUrl) {
        RentProposal rentProposal = rentDao.findById(requestId).orElseThrow(RentProposalNotFoundException::new);
        rentProposal.setState(RentState.ACCEPTED.ordinal());

        emailService.sendMailRequestConfirmation(rentProposal, rentProposal.getArticle().getOwner(), webpageUrl);
    }

    @Override
    @Transactional
    public void rejectRequest(long requestId, String webpageUrl) {
        RentProposal request = rentDao.findById(requestId).orElseThrow(RentProposalNotFoundException::new);
        request.setState(RentState.DECLINED.ordinal());

        emailService.sendMailRequestDenied(request, request.getArticle().getOwner(), webpageUrl);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasRented(User renter, long articleId) {
        if (renter == null)
            return false;

        return rentDao.hasRented(renter.getId(), articleId);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isPresentSameDate(long renterId, long articleId, LocalDate startDate, LocalDate endDate) {
        return rentDao.isPresentSameDate(renterId, articleId, startDate, endDate);
    }
}
