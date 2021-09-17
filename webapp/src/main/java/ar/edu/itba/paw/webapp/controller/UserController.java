package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
import ar.edu.itba.paw.webapp.forms.AccountForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @ModelAttribute(value = "locations")
    public List<Locations> LoadLocations() {
        return Arrays.stream(Locations.values())
                .sorted(Comparator.comparing(Locations::getName))
                .collect(Collectors.toList());
    }

    @RequestMapping("/register")
    public ModelAndView register(@ModelAttribute("accountForm") AccountForm accountForm) {
        final ModelAndView mav = new ModelAndView("account/create");

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
        final ModelAndView mav = new ModelAndView("account/login");
        return mav;
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


}
