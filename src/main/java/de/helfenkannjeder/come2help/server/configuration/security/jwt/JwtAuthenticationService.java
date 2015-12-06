package de.helfenkannjeder.come2help.server.configuration.security.jwt;

import de.helfenkannjeder.come2help.server.domain.ApiUserInfo;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthenticationService {

    private static final String AUTH_HEADER_NAME = "Authorization";
    private final JwtTokenHandler tokenHandler;

    @Autowired
    public JwtAuthenticationService(JwtTokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    public String getAuthenticationToken(final ApiUserInfo user) {
        return tokenHandler.createTokenForUser(user);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        final String authHeader = request.getHeader(AUTH_HEADER_NAME);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.substring(7);
            final ApiUserInfo user = tokenHandler.parseUserFromToken(token);
            if (user != null) {
                return user;
            }
        }
        return null;
    }
}
