package de.helfenkannjeder.come2help.server.configuration.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class CustomOAuthAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final ClientResourceDetails clientResourceDetails;
    private final UserInfoTokenServices tokenService;
    private final AuthorizationCodeAccessTokenProvider accessTokenProvider = new AuthorizationCodeAccessTokenProvider();
    private final MappingJackson2HttpMessageConverter jsonMessageConverter;

    public CustomOAuthAuthenticationProcessingFilter(String path, ClientResourceDetails clientResourceDetails, MappingJackson2HttpMessageConverter jsonMessageConverter) {
        super(path);
        this.clientResourceDetails = clientResourceDetails;
        this.tokenService = new UserInfoTokenServices(clientResourceDetails.getResource().getUserInfoUri(), clientResourceDetails.getClient().getClientId());
        this.accessTokenProvider.setStateMandatory(false);
        this.jsonMessageConverter = jsonMessageConverter;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!"POST".equals(request.getMethod())) {
            throw new BadCredentialsException("Only POST allowed.");
        }

        OAuth2AccessToken accessToken;
        try {
            AccessTokenRequest accessTokenRequest = createAccessTokenRequest(request);
            accessToken = accessTokenProvider.obtainAccessToken(clientResourceDetails.getClient(), accessTokenRequest);
        } catch (OAuth2Exception | UserRedirectRequiredException e) {
            throw new BadCredentialsException("Could not obtain access token", e);
        }

        try {
            return tokenService.loadAuthentication(accessToken.getValue());
        } catch (InvalidTokenException e) {
            throw new BadCredentialsException("Could not obtain user details from token", e);
        }
    }

    private AccessTokenRequest createAccessTokenRequest(HttpServletRequest request) throws IOException {
        AuthorizationToken authorizationToken = (AuthorizationToken) jsonMessageConverter.read(AuthorizationToken.class, new ServletServerHttpRequest(request));
        AccessTokenRequest accessTokenRequest = new DefaultAccessTokenRequest();
        accessTokenRequest.setAuthorizationCode(authorizationToken.getCode());
        accessTokenRequest.setCurrentUri(authorizationToken.getRedirectUri());
        return accessTokenRequest;
    }

    private static class AuthorizationToken {

        private String clientId;
        private String redirectUri;
        private String code;

        public String getClientId() {
            return clientId;
        }

        public String getRedirectUri() {
            return redirectUri;
        }

        public String getCode() {
            return code;
        }
    }
}
