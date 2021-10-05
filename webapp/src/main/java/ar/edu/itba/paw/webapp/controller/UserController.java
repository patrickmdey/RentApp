package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.EmailAlreadyInUseException;
import ar.edu.itba.paw.interfaces.ArticleService;
import ar.edu.itba.paw.interfaces.RentService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.forms.AccountForm;
import ar.edu.itba.paw.webapp.forms.EditAccountForm;
import ar.edu.itba.paw.webapp.forms.PasswordForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RentService rentService;

    @Autowired
    private LoggedUserAdvice userAdvice;

    private final Logger userLogger = LoggerFactory.getLogger(UserController.class);

    private List<Locations> getLocationsOrdered() {
        return Arrays.stream(Locations.values())
                .sorted(Comparator.comparing(Locations::getName))
                .collect(Collectors.toList());
    }

    @RequestMapping("/register")
    public ModelAndView register(@ModelAttribute("accountForm") AccountForm accountForm) {
        final ModelAndView mav = new ModelAndView("account/create");
        mav.addObject("locations", getLocationsOrdered());

        return mav;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@Valid @ModelAttribute("accountForm") AccountForm accountForm,
                                 BindingResult errors) {
        if (errors.hasErrors())
            return register(accountForm);

        userLogger.info("Registering new user --> email: {}, location: {}, type: {}",
                accountForm.getEmail(), accountForm.getLocation(), accountForm.getIsOwner() ? UserType.OWNER : UserType.RENTER);

        userService.register(accountForm.getEmail(), accountForm.getPassword()
                , accountForm.getFirstName(), accountForm.getLastName(), accountForm.getLocation(),
                accountForm.getImg(), accountForm.getIsOwner() ? UserType.OWNER : UserType.RENTER
        );

        return new ModelAndView("redirect:/user/login");
    }

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(value = "error", defaultValue = "false") boolean loginError) {
        ModelAndView mv = new ModelAndView("account/login");

        if (loginError) {
            mv.addObject("loginError", true);
        }
        userLogger.info("User login");
        return mv;
    }

    @RequestMapping("logout")
    public ModelAndView logout() {
        userLogger.info("User logout");
        return new ModelAndView();
    }

    @RequestMapping("/edit")
    public ModelAndView edit(@ModelAttribute("accountForm") EditAccountForm accountForm) {
        final ModelAndView mav = new ModelAndView("account/edit");
        populateForm(accountForm);
        mav.addObject("showPanel", false);
        mav.addObject("locations", getLocationsOrdered());

        return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@Valid @ModelAttribute("accountForm") EditAccountForm accountForm, BindingResult errors) {
        if (errors.hasErrors()) {
            return edit(accountForm);
        }

        userLogger.info("Registering new user --> name: {}, lastName: {}, location: {}, type: {}",
                accountForm.getFirstName(), accountForm.getLastName(), accountForm.getLocation(), accountForm.getIsOwner() ? UserType.OWNER : UserType.RENTER);

        userService.update(userAdvice.loggedUser().getId(),
                accountForm.getFirstName(),
                accountForm.getLastName(),
                accountForm.getLocation(),
                accountForm.getIsOwner()
        );

        reloadGrantedAuthorities(accountForm);
        userLogger.info("reloaded granted authorities");
        return new ModelAndView("redirect:/user/view");
    }

    private void reloadGrantedAuthorities(EditAccountForm accountForm) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<GrantedAuthority> updatedAuthorities = new ArrayList<>();
        updatedAuthorities.add(new SimpleGrantedAuthority(accountForm.getIsOwner() ? "OWNER" : "RENTER"));

        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        Authentication newAuth = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), updatedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    @RequestMapping("/view")
    public ModelAndView view(@ModelAttribute("accountForm") AccountForm accountForm,
                             @RequestParam(value = "page", required = false, defaultValue = "1") Long page) {
        final ModelAndView mav = new ModelAndView("account/view");
        mav.addObject("ownedArticles", articleService.get(null, null,
                null, userAdvice.loggedUser().getId(), null, page));
        mav.addObject("ownedMaxPage", articleService.getMaxPage(null,
                null, userAdvice.loggedUser().getId(), null));

        mav.addObject("rentedArticles", articleService.rentedArticles(userAdvice.loggedUser().getId(), page));
        mav.addObject("rentedMaxPage", articleService.getRentedMaxPage(userAdvice.loggedUser().getId()));
        mav.addObject("locations", getLocationsOrdered());

        populateForm(accountForm);

        return mav;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(HttpServletResponse response) {
        userLogger.info("deleting account --> id: {}, email: {}", userAdvice.loggedUser().getId(), userAdvice.loggedUser().getEmail());
        userService.delete(userAdvice.loggedUser().getId());
        return new ModelAndView("redirect:/user/login");
    }

    private void populateForm(EditAccountForm accountForm) {
        User user = userAdvice.loggedUser();
        accountForm.setFirstName(user.getFirstName());
        accountForm.setLastName(user.getLastName());
        accountForm.setIsOwner(user.getType() == UserType.OWNER);
        accountForm.setLocation((long) user.getLocation().ordinal());
    }

    private void populateForm(AccountForm accountForm) {
        User user = userAdvice.loggedUser();
        accountForm.setFirstName(user.getFirstName());
        accountForm.setLastName(user.getLastName());
        accountForm.setEmail(user.getEmail());
        accountForm.setIsOwner(user.getType() == UserType.OWNER);
        accountForm.setLocation((long) user.getLocation().ordinal());
    }

    @RequestMapping("/my-requests/accepted")
    public ModelAndView acceptedRequests(@RequestParam(value = "page", required = false, defaultValue = "1") Long page) {
        return getRentRequests(userAdvice.loggedUser(), RentState.ACCEPTED, page);
    }

    @RequestMapping("/my-requests/pending")
    public ModelAndView pendingRequests(@RequestParam(value = "page", required = false, defaultValue = "1") Long page) {
        return getRentRequests(userAdvice.loggedUser(), RentState.PENDING, page);
    }

    @RequestMapping("/my-requests/declined")
    public ModelAndView declinedRequests(@RequestParam(value = "page", required = false, defaultValue = "1") Long page) {
        return getRentRequests(userAdvice.loggedUser(), RentState.DECLINED, page);
    }

    @RequestMapping(value = "/my-requests/{requestId}/accept", method = RequestMethod.POST)
    @PreAuthorize("@webSecurity.checkIsRentOwner(authentication, #requestId)")
    public ModelAndView acceptRequest(@PathVariable("requestId") Long requestId) {
        userLogger.info("accepting request with id {}", requestId);
        rentService.acceptRequest(requestId);
        return new ModelAndView("redirect:/user/my-requests/accepted");
    }

    @RequestMapping(value = "/my-requests/{requestId}/delete", method = RequestMethod.POST)
    @PreAuthorize("@webSecurity.checkIsRentOwner(authentication, #requestId)")
    public ModelAndView rejectRequest(@PathVariable("requestId") Long requestId) {
        userLogger.info("rejecting request with id {}", requestId);
        rentService.rejectRequest(requestId);
        return new ModelAndView("redirect:/user/my-requests/declined");
    }

    private ModelAndView getRentRequests(User user, RentState state, Long page) {
        final ModelAndView mav = new ModelAndView("account/myRequests");
        List<RentProposal> receivedProposals = rentService.ownerRequests(user.getId(), state.ordinal(), page);
        List<RentProposal> sentProposals = rentService.sentRequests(user.getId(), state.ordinal(), page);

        mav.addObject("state", state.name());
        mav.addObject("receivedProposals", receivedProposals);
        mav.addObject("sentProposals", sentProposals);
        mav.addObject("receivedMaxPage", rentService.getReceivedMaxPage(user.getId(), state.ordinal()));
        mav.addObject("sentMaxPage", rentService.getSentMaxPage(user.getId(), state.ordinal()));
        return mav;
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.GET)
    public ModelAndView updatePassword(@ModelAttribute(value = "passwordForm") PasswordForm passwordForm) {
        return new ModelAndView("account/updatePassword");
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public ModelAndView updatePassword(@Valid @ModelAttribute(value = "passwordForm") PasswordForm passwordForm, BindingResult errors) {
        ModelAndView mv = new ModelAndView("account/updatePassword");

        userLogger.info("updating password");
        if (errors.hasErrors()) {
            mv.addObject("showPanel", false);
            return mv;
        }
        mv.addObject("showPanel", true);

        userService.updatePassword(userAdvice.loggedUser().getId(), passwordForm.getPassword());
        return mv;
    }
}