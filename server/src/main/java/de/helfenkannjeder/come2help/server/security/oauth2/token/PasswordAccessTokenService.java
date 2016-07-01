package de.helfenkannjeder.come2help.server.security.oauth2.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.helfenkannjeder.come2help.server.configuration.security.ClientResourceDetails;
import de.helfenkannjeder.come2help.server.security.oauth2.OAuth2Client;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

/**
 * @author Valentin Zickner
 */
public class PasswordAccessTokenService implements AccessTokenService {

    private final ObjectMapper objectMapper;
    private ClientResourceDetails clientResourceDetails;

    public PasswordAccessTokenService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void setClientResourceDetails(ClientResourceDetails clientResourceDetails) {
        this.clientResourceDetails = clientResourceDetails;
    }

    @Override
    public OAuth2AccessToken getAccessToken(HttpServletRequest request) throws IOException {
        LoginInformation loginInformation = objectMapper.readValue(request.getReader(), LoginInformation.class);
        OAuth2Client client = new OAuth2Client(clientResourceDetails.getClient().getAccessTokenUri(),
                clientResourceDetails.getClient().getClientId(),
                clientResourceDetails.getClient().getClientSecret());
        return client.getAccessToken(loginInformation.getEmail(),
                loginInformation.getPassword(), Collections.singletonList("default"));
    }

    private static class LoginInformation {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
