package ar.edu.itba.paw.webapp.filters;

import ar.edu.itba.paw.webapp.utils.ApiUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

public class CacheFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // TODO: add cache headers to response
        response.setHeader("Cache-Control", "public, max-age=" + ApiUtils.CACHE_MAX_AGE + ", immutable");
        response.setHeader("Expires", LocalDate.now().plusYears(1).atStartOfDay().atZone(ZoneId.systemDefault()).toString());
        filterChain.doFilter(request, response);
    }
}
