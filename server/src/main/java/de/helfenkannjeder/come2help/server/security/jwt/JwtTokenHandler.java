package de.helfenkannjeder.come2help.server.security.jwt;

import de.helfenkannjeder.come2help.server.security.UserAuthentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenHandler {

    private static final int DAY_DURATION = 1000 * 60 * 60;
    private static final String CLAIM_AUTH_PROVIDER = "provider";
    private static final String CLAIM_EXTERNAL_ID = "ext-id";
    private static final String CLAIM_GIVEN_NAME = "name";
    private static final String CLAIM_SURNAME = "surname";
    private static final String CLAIM_EMAIL = "email";
    private static final String CLAIM_AUTHORITIES = "authorities";

    @Value("${api.jwt.secretKey}")
    private String secretKey;

    public UserAuthentication parseUserFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        String internalId = claims.getSubject();
        String authProvider = (String) claims.get(CLAIM_AUTH_PROVIDER);
        String externalId = (String) claims.get(CLAIM_EXTERNAL_ID);
        String givenName = (String) claims.get(CLAIM_GIVEN_NAME);
        String surname = (String) claims.get(CLAIM_SURNAME);
        String email = (String) claims.get(CLAIM_EMAIL);
        List<String> authorities = (List<String>) claims.get(CLAIM_AUTHORITIES);

        return new UserAuthentication(internalId != null ? Long.valueOf(internalId) : null, authProvider, externalId, givenName, surname, email, authorities);
    }

    public String createTokenForUser(UserAuthentication user) {
        return Jwts.builder()
                .setSubject(user.getInternalId() != null ? user.getInternalId() + "" : null)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim(CLAIM_AUTH_PROVIDER, user.getAuthProvider())
                .claim(CLAIM_EXTERNAL_ID, user.getExternalId())
                .claim(CLAIM_GIVEN_NAME, user.getGivenName())
                .claim(CLAIM_SURNAME, user.getSurname())
                .claim(CLAIM_EMAIL, user.getEmail())
                .claim(CLAIM_AUTHORITIES, user.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + DAY_DURATION))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
