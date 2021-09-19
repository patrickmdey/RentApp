package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ArticleService;
import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.RentService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
import ar.edu.itba.paw.webapp.forms.AccountForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Autowired
    ArticleService articleService;

    @Autowired
    RentService rentService;

    @ModelAttribute(value = "locations")
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
                accountForm.getIsOwner() ? UserType.Owner : UserType.Renter
        );

        return login();
    }


    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("account/login");
    }

    @RequestMapping("/logout")
    public ModelAndView logout() {
        return new ModelAndView("marketplace");
    }


    @RequestMapping("/edit")
    public ModelAndView edit(@ModelAttribute("accountForm") AccountForm accountForm) {
        final ModelAndView mav = new ModelAndView("account/edit");

        populateForm(accountForm);
        mav.addObject("showPanel", false);

        return mav;

    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@Valid @ModelAttribute("accountForm") AccountForm accountForm, BindingResult errors) {

        final ModelAndView mav = new ModelAndView("account/edit");


        if (errors.hasErrors() && errors.getFieldErrors().stream()
                .filter(t -> t.getField().compareToIgnoreCase("confirmPassword") != 0 && t.getField().compareToIgnoreCase("password") != 0)
                .count() != 0) {
            mav.addObject("showPanel", false);
            return mav;
        }

        userService.update(loggedUser().getId(),
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
    public ModelAndView view(@ModelAttribute("accountForm") AccountForm accountForm) {
        final ModelAndView mav = new ModelAndView("account/view");
        mav.addObject("articles", articleService.findByOwner(loggedUser().getId()));
        populateForm(accountForm);

        return mav;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(HttpServletResponse response) throws IOException {
        userService.delete(loggedUser().getId());

        response.sendRedirect("logout");

        return;
    }

    private AccountForm populateForm(AccountForm accountForm) {
        User user = loggedUser();
        accountForm.setEmail(user.getEmail());
        accountForm.setFirstName(user.getFirstName());
        accountForm.setLastName(user.getLastName());
        accountForm.setIsOwner(user.getType() == UserType.Owner);
        accountForm.setLocation(user.getLocation());

        return accountForm;
    }

    @RequestMapping("/my-requests")
    public ModelAndView myAccount() {
        final ModelAndView mav = new ModelAndView("account/myRequests");
        List<RentProposal> rentProposals = rentService.ownerRequests(loggedUser().getId());

        mav.addObject("requests", rentProposals);
        return mav;
    }

    @RequestMapping(value = "/my-requests/{requestId}/accept", method = RequestMethod.POST)
    @PreAuthorize("@webSecurity.checkIsRentOwner(authentication, #requestId)")
    public ModelAndView acceptRequest(@PathVariable("requestId") Long requestId) {
        rentService.acceptRequest(requestId);
        return myAccount();
    }

    @RequestMapping(value = "/my-requests/{requestId}/delete", method = RequestMethod.POST)
    @PreAuthorize("@webSecurity.checkIsRentOwner(authentication, #requestId)")
    public ModelAndView deleteRequest(@PathVariable("requestId") Long requestId) {
        rentService.deleteRequest(requestId);
        return myAccount();
    }
}