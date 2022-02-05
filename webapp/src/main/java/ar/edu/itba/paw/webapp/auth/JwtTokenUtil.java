package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.security.Key;
import java.time.LocalDate;

public class JwtTokenUtil {
//    TODO: use this instead
//    @Value("classpath:secret")
//    private Resource key;


    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);;

    private static byte[] readKey(Resource resource) throws IOException {
        return StreamUtils.copyToByteArray(resource.getInputStream());
    }

    public static boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public static String getUsername(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        String subject = claims.getSubject();
        return subject.split(",")[0];
    }

    public static String generateAccessToken(User user) {
        // TODO: add duration
        // new LocalDate().plusDays(7)
        // signWith(SignatureAlgorithm.HS512, readKey(key))
        return Jwts.builder().setSubject(user.getEmail() + "," + user.getId()).signWith(SECRET_KEY).compact();
    }
}
