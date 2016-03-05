package de.helfenkannjeder.come2help.server.security.jwt;

import de.helfenkannjeder.come2help.server.domain.User;
import de.helfenkannjeder.come2help.server.security.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class JwtAuthenticationService {

    private static final String AUTH_HEADER_NAME = "Authorization";
    private final JwtTokenHandler tokenHandler;

    @Autowired
    public JwtAuthenticationService(JwtTokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    public String getAuthenticationToken(final UserAuthentication user) {
        return tokenHandler.createTokenForUser(user);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        final String authHeader = request.getHeader(AUTH_HEADER_NAME);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.substring(7);

            return tokenHandler.parseUserFromToken(token);
        }
        return null;
    }

    public void addTokenHeaderForUser(HttpHeaders response, User user) {
        response.add(AUTH_HEADER_NAME, getAuthenticationToken(user.createUserAuthentication()));
    }
}
