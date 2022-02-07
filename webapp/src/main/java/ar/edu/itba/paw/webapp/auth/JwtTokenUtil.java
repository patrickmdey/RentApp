package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import java.io.IOException;
import java.util.Date;

@Component
public class JwtTokenUtil {
    private static final Resource secretKey = new ClassPathResource("secret");

    private static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;

    private static byte[] readKey() throws IOException {
        return StreamUtils.copyToByteArray(secretKey.getInputStream());
    }

    public static boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(readKey())).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IOException e) {
            return false;
        }
    }

    public static String getUsername(String token) throws IOException {
        Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(readKey())).build()
                .parseClaimsJws(token).getBody();
        String subject = claims.getSubject();
        return subject.split(",")[0];
    }

    public static String generateAccessToken(User user) throws IOException {
        return Jwts.builder().setSubject(user.getEmail() + "," + user.getId())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(readKey()), SignatureAlgorithm.HS256)
                .compact();
    }
}