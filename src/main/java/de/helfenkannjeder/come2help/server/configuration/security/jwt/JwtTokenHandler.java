package de.helfenkannjeder.come2help.server.configuration.security.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenHandler {

    private static final int DAY_DURATION = 1000 * 60 * 60;

    @Value("${api.jwt.secretKey}")
    private String secretKey;
    @Autowired
    private UserDetailsService userDetailsService;

    public UserDetails parseUserFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userDetailsService.loadUserByUsername(username);
    }

    public String createTokenForUser(UserDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim("test-claim", "Test Claim Content")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + DAY_DURATION))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
