package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.RentService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.interfaces.ArticleService;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RentAccessDecisionVoter implements AccessDecisionVoter<FilterInvocation> {

    @Autowired
    RentService rentService;

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
        //return !configAttribute.getAttribute().equals("anonymous");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation filterInvocation, Collection<ConfigAttribute> collection) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return ACCESS_ABSTAIN;
        }

        HttpServletRequest request = filterInvocation.getHttpRequest();

        for (ConfigAttribute attribute : collection) {
            if ("IS_AUTHENTICATED_ANONYMOUSLY".equals(attribute.getAttribute())) {
                return ACCESS_GRANTED;
            }
        }

        if(filterInvocation != null) {
            User loggedUser;
            try {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                loggedUser = userService.findByEmail(userDetails.getUsername()).orElseThrow(Exception::new);
            } catch (Exception e) {
                return ACCESS_DENIED;
            }

            List<String> regexList = Arrays.asList("/user/my-account/(\\d+)/accept", "/user/my-account/(\\d+)/delete");
            String url = filterInvocation.getRequestUrl();
            System.out.println(url);
            for (String reg : regexList) {
                Matcher matcher = Pattern.compile(reg).matcher(url);
                if (matcher.matches()) {
                    long rentId = Long.parseLong(matcher.group(1));
                    Optional<RentProposal> prop = rentService.findById(rentId);
                    if (!prop.isPresent())
                        return ACCESS_DENIED;

                    Optional<Article> article = articleService.findById(prop.get().getArticleId());
                    if (!article.isPresent() || article.get().getIdOwner() != loggedUser.getId())
                        return ACCESS_DENIED;

                    return ACCESS_GRANTED;
                }
            }
        }
        return ACCESS_ABSTAIN;
    }
}
