package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import java.io.IOException;
import java.util.Date;

public class JwtTokenUtil {
    private final byte[] key;

    public JwtTokenUtil(Resource resource) throws IOException {
        this.key = StreamUtils.copyToByteArray(resource.getInputStream());
    }

    private static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(key)).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(key)).build()
                .parseClaimsJws(token).getBody();
        String subject = claims.getSubject();
        return subject.split(",")[0];
    }

    public String generateAccessToken(User user) {
        return Jwts.builder().setSubject(user.getEmail() + "," + user.getId())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(key), SignatureAlgorithm.HS256)
                .compact();
    }
}