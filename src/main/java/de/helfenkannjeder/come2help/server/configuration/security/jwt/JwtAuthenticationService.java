package de.helfenkannjeder.come2help.server.configuration.security.jwt;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthenticationService {

    private static final String AUTH_HEADER_NAME = "Authorization";
    private final JwtTokenHandler tokenHandler;

    @Autowired
    public JwtAuthenticationService(JwtTokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    public String getAuthenticationToken(final UserDetails user) {
        return tokenHandler.createTokenForUser(user);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        final String authHeader = request.getHeader(AUTH_HEADER_NAME);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.substring(7);
            final UserDetails userDetails = tokenHandler.parseUserFromToken(token);
            if (userDetails != null) {
                return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
            }
        }
        return null;
    }

}
