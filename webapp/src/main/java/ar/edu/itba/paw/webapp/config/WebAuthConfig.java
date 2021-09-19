package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.interfaces.ArticleService;
import ar.edu.itba.paw.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

@ComponentScan({"ar.edu.itba.paw.webapp.auth"})
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService pawUserDetailService;

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(pawUserDetailService)
                .passwordEncoder(passwordEncoder());
    }

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
                .antMatchers(HttpMethod.POST, "/user/delete").fullyAuthenticated()
                .antMatchers("/create-article", "user/my-requests").hasAuthority("OWNER")
                .antMatchers(HttpMethod.POST,
                      "/my-requests/accept/{requestId}", "/my-requests/{requestId}/delete/").hasAuthority("OWNER")
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
                .antMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/403");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


}
