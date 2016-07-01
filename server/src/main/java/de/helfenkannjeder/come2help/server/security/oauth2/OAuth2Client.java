package de.helfenkannjeder.come2help.server.security.oauth2;

import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.List;

/**
 * @author Valentin Zickner
 */
public class OAuth2Client {

    private static final String GRANT_TYPE = "password";

    private final String tokenUrl;
    private final String clientId;
    private final String secret;

    public OAuth2Client(String tokenUrl, String clientId, String secret) {
        this.tokenUrl = tokenUrl;
        this.clientId = clientId;
        this.secret = secret;
    }

    public OAuth2AccessToken getAccessToken(String username, String password, List<String> scopes) {
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();

        resource.setAccessTokenUri(tokenUrl);
        resource.setClientId(clientId);
        resource.setClientSecret(secret);
        resource.setGrantType(GRANT_TYPE);
        resource.setScope(scopes);

        resource.setUsername(username);
        resource.setPassword(password);

        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        return new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext(atr)).getAccessToken();
    }
}
