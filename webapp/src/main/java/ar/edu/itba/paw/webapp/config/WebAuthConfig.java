package ar.edu.itba.paw.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

@ComponentScan({ "ar.edu.itba.paw.webapp.auth"})
@Configuration
@EnableWebSecurity
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService pawUserDetailService;

    @Override
    protected void configure (AuthenticationManagerBuilder auth) throws Exception {
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
//                .sessionManagement()
//                .invalidSessionUrl("/user/login")
//                .
//                .and()
                .authorizeRequests()
                .anyRequest().permitAll()
//                    .antMatchers("**").permitAll()
//                    .antMatchers("/marketplace/create-article").hasRole("OWNER")
//                .antMatchers("/user/view","/user/edit").hasAnyRole("OWNER","RENTER")
                .and().formLogin()
                .defaultSuccessUrl("/user/view", false)
                    .loginPage("/user/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                .and().rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .userDetailsService(pawUserDetailService)
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
