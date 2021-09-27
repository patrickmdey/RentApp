package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ArticleService;
import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.RentService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.forms.AccountForm;
import ar.edu.itba.paw.webapp.forms.EditAccountForm;
import ar.edu.itba.paw.webapp.forms.PasswordForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @ModelAttribute(value = "locations") //TODO: sacar esto
    public List<Locations> LoadLocations() {
        return Arrays.stream(Locations.values())
                .sorted(Comparator.comparing(Locations::getName))
                .collect(Collectors.toList());
    }

    @RequestMapping("/register")
    public ModelAndView register(@ModelAttribute("accountForm") AccountForm accountForm) {
        final ModelAndView mav = new ModelAndView("account/create");
        mav.addObject("locations", Arrays.stream(Locations.values())
                .sorted(Comparator.comparing(Locations::getName))
                .collect(Collectors.toList()));

        return mav;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@Valid @ModelAttribute("accountForm") AccountForm accountForm,
                                 BindingResult errors) {

        if (errors.hasErrors())
            return register(accountForm);

        Optional<User> user = userService.register(
                accountForm.getEmail(),
                accountForm.getPassword(),
                accountForm.getConfirmPassword(),
                accountForm.getFirstName(),
                accountForm.getLastName(),
                accountForm.getLocation(),
                accountForm.getImg(),
                accountForm.getIsOwner() ? UserType.Owner : UserType.Renter
        );

        return login();
    }


    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("account/login");
    }


    @RequestMapping("/edit")
    public ModelAndView edit(@ModelAttribute("accountForm") EditAccountForm accountForm) {
        final ModelAndView mav = new ModelAndView("account/edit");

        populateForm(accountForm);
        mav.addObject("showPanel", false);

        return mav;

    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@Valid @ModelAttribute("accountForm") EditAccountForm accountForm, BindingResult errors) {

        final ModelAndView mav = new ModelAndView("account/edit");

        if (errors.hasErrors()) {
            mav.addObject("showPanel", false);
            return mav;
        }

        userService.update(userAdvice.loggedUser().getId(),
                accountForm.getFirstName(),
                accountForm.getLastName(),
                accountForm.getEmail(),
                accountForm.getLocation(),
                accountForm.getIsOwner()
        );

        mav.addObject("showPanel", true);

        return mav;
    }


    @RequestMapping("/view")
    public ModelAndView view(@ModelAttribute("accountForm") EditAccountForm accountForm,
                             @RequestParam(value = "page", required = false, defaultValue = "1") Long page) {
        final ModelAndView mav = new ModelAndView("account/view");
        mav.addObject("articles", articleService.get(null, null,
                null, userAdvice.loggedUser().getId(), null, page));
        mav.addObject("maxPage", articleService.getMaxPage(null,
                null, userAdvice.loggedUser().getId(), null));
        populateForm(accountForm);

        return mav;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(HttpServletResponse response) throws IOException {
        userService.delete(userAdvice.loggedUser().getId());

        response.sendRedirect("logout");
    }

    private void populateForm(EditAccountForm accountForm) {
        User user = userAdvice.loggedUser();
        accountForm.setEmail(user.getEmail());
        accountForm.setFirstName(user.getFirstName());
        accountForm.setLastName(user.getLastName());
        accountForm.setIsOwner(user.getType() == UserType.Owner);
        accountForm.setLocation(user.getLocation());
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
        rentService.acceptRequest(requestId);
        return new ModelAndView("redirect:/user/my-requests/accepted");
    }

    @RequestMapping(value = "/my-requests/{requestId}/delete", method = RequestMethod.POST)
    @PreAuthorize("@webSecurity.checkIsRentOwner(authentication, #requestId)")
    public ModelAndView rejectRequest(@PathVariable("requestId") Long requestId) {
        rentService.rejectRequest(requestId);
        return new ModelAndView("redirect:/user/my-requests/declined");
    }

    private ModelAndView getRentRequests(User user, RentState state, Long page){
        final ModelAndView mav = new ModelAndView("account/myRequests");
        List<RentProposal> rentProposals = rentService.ownerRequests(user.getId(), state.ordinal(), page);

        mav.addObject("requests", rentProposals);
        mav.addObject("state", state.name());
        mav.addObject("maxPage", rentService.getMaxPage(user.getId(), state.ordinal()));

        return mav;
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.GET)
    public ModelAndView updatePassword(@ModelAttribute(value = "passwordForm") PasswordForm passwordForm) {
        return new ModelAndView("account/updatePassword");
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public ModelAndView updatePassword(@Valid @ModelAttribute(value = "passwordForm") PasswordForm passwordForm, BindingResult errors) {
        ModelAndView mv = new ModelAndView("account/updatePassword");

        if (errors.hasErrors()) {
            mv.addObject("showPanel", false);
            return mv;
        }

        mv.addObject("showPanel", true);

        userService.updatePassword(userAdvice.loggedUser().getId(), passwordForm.getPassword());

        return mv;
    }


}