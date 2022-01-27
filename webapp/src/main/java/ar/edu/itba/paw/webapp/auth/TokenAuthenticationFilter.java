package ar.edu.itba.paw.webapp.auth;

import com.sun.tools.javac.util.List;
import com.sun.xml.internal.rngom.parse.host.Base;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

@Secured({})
@Provider
@Component
@Priority(Priorities.AUTHENTICATION)
public class TokenAuthenticationFilter extends OncePerRequestFilter {

//    private static final String REALM = "example";
//    private static final String AUTHENTICATION_SCHEME = "Bearer";
//    private static final String SECRET_KEY = "secret";

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
        }

        if (!header.startsWith("Bearer ")) {
            if (!header.startsWith("Basic ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String encodedCredentials = header.split(" ")[1];
            String[] credentials = new String(Base64.getDecoder().decode(encodedCredentials)).split(":");
            if (credentials.length != 2) {
                filterChain.doFilter(request, response);
                return;
            }

            String username = credentials[0].trim();
            String password = credentials[1].trim();
            try {
                Authentication authenticate = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(username, password));

                UserDetails user = (UserDetails) authenticate.getPrincipal();
                response.setHeader(HttpHeaders.AUTHORIZATION, JwtTokenUtil.generateAccessToken(user));
                return;
            } catch (BadCredentialsException ex) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        if (!JwtTokenUtil.validate(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get user identity and set it on the spring security context
        UserDetails userDetails = userDetailsService.loadUserByUsername(JwtTokenUtil.getUsername(token));

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ?
                        List.of(null) : userDetails.getAuthorities()
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private boolean isEmpty(String header) {
        return header == null || header.isEmpty();
    }


}
