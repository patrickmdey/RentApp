package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.ArticleAccessDecisionVoter;
import ar.edu.itba.paw.webapp.auth.RentAccessDecisionVoter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ComponentScan({ "ar.edu.itba.paw.webapp.auth"})
@Configuration
@EnableWebSecurity
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService pawUserDetailService;

    @Autowired
    ArticleAccessDecisionVoter articleAccessDecisionVoter;

    @Autowired
    RentAccessDecisionVoter rentAccessDecisionVoter;

    @Override
    protected void configure (AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(pawUserDetailService)
                .passwordEncoder(passwordEncoder());
    }

    /*
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter <?>> decisionVoters = Arrays.asList(
//                new AuthenticatedVoter(),
//                new RoleVoter(),
//                new WebExpressionVoter(),
                articleAccessDecisionVoter,
                rentAccessDecisionVoter
        );
        return new UnanimousBased(decisionVoters);
    }
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .invalidSessionUrl("/")
                .and()
                .authorizeRequests()
                .antMatchers("/user/login", "/user/register").anonymous()
                .antMatchers("/user/view", "/user/edit").authenticated()
                .antMatchers(HttpMethod.POST,"/user/delete").fullyAuthenticated()
                .antMatchers("/create-article","user/my-account").hasAuthority("OWNER")
                //.antMatchers(HttpMethod.POST,
                  //      "/my-account/accept/{requestId}", "/my-account/{requestId}/delete/").hasAuthority("OWNER")
                //.accessDecisionManager(accessDecisionManager())
                .anyRequest().permitAll()

                .and().formLogin()
                .defaultSuccessUrl("/", false)
                .loginPage("/user/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().rememberMe()
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .userDetailsService(pawUserDetailService)
                .key("Super clave re copada que nadie nunca va a adivinar por que somoes el mejor grupo de todo el mundo")
                .rememberMeParameter("rememberMe")
                .and().logout()
                    .logoutUrl("/user/logout")
                    .logoutSuccessUrl("/user/login")

                .and().exceptionHandling()
                    .accessDeniedPage("/403")
                .and().csrf()
                    .disable();


    }

    @Override
    public void configure(WebSecurity web) throws Exception {
       web.ignoring()
               .antMatchers("/css/**","/js/**","/img/**","/favicon.ico","/403");
    }


}
