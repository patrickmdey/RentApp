package ar.edu.itba.paw.webapp.filters;

import ar.edu.itba.paw.webapp.auth.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.annotation.Priority;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

@Secured({})
@Provider
@Component
@Priority(Priorities.AUTHENTICATION)
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header)) {
            filterChain.doFilter(request, response);
            return;
        } else if (header.startsWith("Basic ")) {
            tryBasicAuthentication(header, request, response);
        } else if (header.startsWith("Bearer ")) {
            tryBearerAuthentication(header, request);
        }

        filterChain.doFilter(request, response);
    }

    private void tryBearerAuthentication(String header, HttpServletRequest request) {
        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        if (!JwtTokenUtil.validate(token))
            return;

        // Get user identity and set it on the spring security context
        UserDetails userDetails = userDetailsService.loadUserByUsername(JwtTokenUtil.getUsername(token));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ? Collections.emptyList() : userDetails.getAuthorities()
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void tryBasicAuthentication(String header, HttpServletRequest request, HttpServletResponse response) {
        String encodedCredentials = header.split(" ")[1];
        try {
            String[] credentials = new String(Base64.getDecoder().decode(encodedCredentials)).split(":");
            if (credentials.length != 2)
                return;

            String username = credentials[0].trim();
            String password = credentials[1].trim();

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username, password
            );

            Authentication authenticate = authenticationManager
                    .authenticate(authentication);

            UserDetails user = (UserDetails) authenticate.getPrincipal();
            response.setHeader(HttpHeaders.AUTHORIZATION, JwtTokenUtil.generateAccessToken(user));

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (BadCredentialsException | IllegalArgumentException ignored) {

        } // TODO ver de informar que no se mandó bien el token base64

    }

    private boolean isEmpty(String header) {
        return header == null || header.isEmpty();
    }
}
