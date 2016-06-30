package de.helfenkannjeder.come2help.server.security.oauth2.token;

import de.helfenkannjeder.come2help.server.configuration.security.ClientResourceDetails;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Valentin Zickner
 */
public class AuthorizationCodeAccessTokenService implements AccessTokenService {

    private ClientResourceDetails clientResourceDetails;
    private final MappingJackson2HttpMessageConverter jsonMessageConverter;
    private final AuthorizationCodeAccessTokenProvider accessTokenProvider = new AuthorizationCodeAccessTokenProvider();

    public AuthorizationCodeAccessTokenService(MappingJackson2HttpMessageConverter jsonMessageConverter) {
        this.jsonMessageConverter = jsonMessageConverter;
        this.accessTokenProvider.setStateMandatory(false);
    }

    @Override
    public void setClientResourceDetails(ClientResourceDetails clientResourceDetails) {
        this.clientResourceDetails = clientResourceDetails;
    }

    @Override
    public OAuth2AccessToken getAccessToken(HttpServletRequest request) throws IOException {
        OAuth2AccessToken accessToken;
        try {
            AccessTokenRequest accessTokenRequest = createAccessTokenRequest(request);
            accessToken = accessTokenProvider.obtainAccessToken(clientResourceDetails.getClient(), accessTokenRequest);
        } catch (OAuth2Exception | UserRedirectRequiredException e) {
            throw new BadCredentialsException("Could not obtain access token", e);
        }

        return accessToken;
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
