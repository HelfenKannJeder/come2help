package de.helfenkannjeder.come2help.server.security.oauth2;

import de.helfenkannjeder.come2help.server.configuration.security.ClientResourceDetails;
import de.helfenkannjeder.come2help.server.security.oauth2.token.AccessTokenService;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomOAuthAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final UserInfoTokenServices tokenService;
    private final AccessTokenService accessTokenService;

    public CustomOAuthAuthenticationProcessingFilter(String path, ClientResourceDetails clientResourceDetails, AccessTokenService accessTokenService) {
        super(path);
        this.tokenService = new UserInfoTokenServices(clientResourceDetails.getResource().getUserInfoUri(), clientResourceDetails.getClient().getClientId());
        this.accessTokenService = accessTokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!"POST".equals(request.getMethod())) {
            throw new BadCredentialsException("Only POST allowed.");
        }

        OAuth2AccessToken accessToken = accessTokenService.getAccessToken(request);

        try {
            return tokenService.loadAuthentication(accessToken.getValue());
        } catch (InvalidTokenException e) {
            throw new BadCredentialsException("Could not obtain user details from token", e);
        }
    }

}
