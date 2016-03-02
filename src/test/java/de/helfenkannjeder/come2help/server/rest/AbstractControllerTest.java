package de.helfenkannjeder.come2help.server.rest;

import de.helfenkannjeder.come2help.server.configuration.Come2HelpApplication;
import de.helfenkannjeder.come2help.server.security.UserAuthentication;
import de.helfenkannjeder.come2help.server.util.TestDataInitializer;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest
@SpringApplicationConfiguration(classes = Come2HelpApplication.class)
public abstract class AbstractControllerTest {

    @Autowired
    private TestDataInitializer testDataInitializer;

    @Autowired
    private WebApplicationContext wac;

    MockMvc mockMvc;

    void authenticate(String... authorities) {
        StringBuilder authoritiesBuilder = new StringBuilder();
        for (String authority : authorities) {
            authoritiesBuilder.append(",").append(authority);
        }
        ClientDetails client = new BaseClientDetails("clientId", null, "read", "client_credentials", authoritiesBuilder.substring(1));
        OAuth2Authentication authentication = new OAuth2Authentication(new TokenRequest(null, "clientId", null, "client_credentials").createOAuth2Request(client), null);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    void dummyAuthentication(String... authorities) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(UserAuthentication.createDummyAuthentication(authorities));
        SecurityContextHolder.setContext(context);
    }

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Before
    public void initDatabase() {
        testDataInitializer.initDatabase();
    }

    @After
    public void clearAuthentication() {
        SecurityContextHolder.clearContext();
    }

    @After
    public void cleanupDatabase() {
        testDataInitializer.cleanupDatabase();
    }
}
