package de.helfenkannjeder.come2help.server.rest;

import de.helfenkannjeder.come2help.server.configuration.Come2HelpApplication;
import de.helfenkannjeder.come2help.server.security.Authorities;
import de.helfenkannjeder.come2help.server.util.TestDataInitializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Valentin Zickner
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest
@SpringApplicationConfiguration(classes = Come2HelpApplication.class)
public class AbilitiesControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private TestDataInitializer testDataInitializer;

    private MockMvc mockMvc;

    @Before
    public void authenticate() {
        ClientDetails client = new BaseClientDetails("clientId", null, "read", "client_credentials", Authorities.USER);
        OAuth2Authentication authentication = new OAuth2Authentication(new TokenRequest(null, "clientId", null, "client_credentials").createOAuth2Request(client), null);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    @After
    public void clearAuthentication() {
        SecurityContextHolder.clearContext();
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
    public void cleanupDatabase() {
        testDataInitializer.cleanupDatabase();
    }

    @Test
    public void getAbilities_withAbilitiesFromDatabase_returnsSerializedAbilities() throws Exception {
        mockMvc.perform(get("/abilities").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}