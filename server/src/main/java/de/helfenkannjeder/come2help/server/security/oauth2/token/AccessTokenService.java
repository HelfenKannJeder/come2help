package de.helfenkannjeder.come2help.server.security.oauth2.token;

import de.helfenkannjeder.come2help.server.configuration.security.ClientResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Valentin Zickner
 */
public interface AccessTokenService {
    void setClientResourceDetails(ClientResourceDetails clientResourceDetails);
    OAuth2AccessToken getAccessToken(HttpServletRequest httpServletRequest) throws IOException;
}
