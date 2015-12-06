package de.helfenkannjeder.come2help.server.configuration.security.jwt;

import de.helfenkannjeder.come2help.server.domain.ApiUserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenHandler {

    private static final int DAY_DURATION = 1000 * 60 * 60;
    private static final String CLAIM_EXTERNAL_ID = "ext-id";
    private static final String CLAIM_GIVEN_NAME = "name";
    private static final String CLAIM_SURNAME = "surname";
    private static final String CLAIM_EMAIL = "email";

    @Value("${api.jwt.secretKey}")
    private String secretKey;

    public ApiUserInfo parseUserFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        String internalId = claims.getSubject();
        String externalId = (String) claims.get(CLAIM_EXTERNAL_ID);
        String givenName = (String) claims.get(CLAIM_GIVEN_NAME);
        String surname = (String) claims.get(CLAIM_SURNAME);
        String email = (String) claims.get(CLAIM_EMAIL);

        return new ApiUserInfo(internalId, externalId, givenName, surname, email);
    }

    public String createTokenForUser(ApiUserInfo user) {
        return Jwts.builder()
                .setSubject(user.getInternalId())
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim(CLAIM_EXTERNAL_ID, user.getExternalId())
                .claim(CLAIM_GIVEN_NAME, user.getGivenName())
                .claim(CLAIM_SURNAME, user.getSurname())
                .claim(CLAIM_EMAIL, user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + DAY_DURATION))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
