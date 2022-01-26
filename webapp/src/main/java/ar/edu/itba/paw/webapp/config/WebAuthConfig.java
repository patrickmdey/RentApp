package ar.edu.itba.paw.webapp.config;

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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ComponentScan({"ar.edu.itba.paw.webapp.auth"})
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService pawUserDetailService;

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
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .invalidSessionUrl("/")
                .and()
                .authorizeRequests()
                .antMatchers("/user/login", "/user/register").anonymous()
                .antMatchers("/user/view", "/user/edit").authenticated()
                .antMatchers(HttpMethod.POST, "/user/delete").fullyAuthenticated()
                .antMatchers("/user/my-requests/sent/**").authenticated()
                .antMatchers("/user/my-requests/received/**").hasAuthority("OWNER")
                .antMatchers("/article/create").hasAuthority("OWNER")
                .antMatchers("/review/{articleId}/create").authenticated()
                .anyRequest().permitAll()
                .and().exceptionHandling()
                .accessDeniedPage("/403")
                .and().csrf()
                .disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/403");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
