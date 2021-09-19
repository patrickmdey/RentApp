package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.ArticleService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ArticleAccessDecisionVoter implements AccessDecisionVoter<FilterInvocation> {

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
        //return !((WebExpressionConfigAttribute) configAttribute).getAuthorizeExpression().getExpressionString().equals("anonymous");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation filterInvocation, Collection<ConfigAttribute> collection) {
        if(filterInvocation != null) {
            User loggedUser;
            try {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                loggedUser = userService.findByEmail(userDetails.getUsername()).orElseThrow(Exception::new);
            } catch (Exception e) {
                return ACCESS_DENIED;
            }

            List<String> regexList = Collections.singletonList("/article/(\\d+)/edit");
            String url = filterInvocation.getRequestUrl();
            System.out.println(url);
            for (String reg : regexList) {
                Matcher matcher = Pattern.compile(reg).matcher(url);
                if (matcher.matches()) {
                    long articleId = Long.parseLong(matcher.group(1));
                    Optional<Article> article = articleService.findById(articleId);

                    if (!article.isPresent() || loggedUser.getId() == article.get().getIdOwner())
                        return ACCESS_DENIED;

                    return ACCESS_GRANTED;


                }
            }
        }
        return ACCESS_ABSTAIN;
    }
}