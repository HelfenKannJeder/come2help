package de.helfenkannjeder.come2help.server.util.mail;

import de.helfenkannjeder.come2help.server.cucumber.configuration.TestApplicationConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.subethamail.wiser.Wiser;

/**
 * based on http://blog.codeleak.pl/2014/09/testing-mail-code-in-spring-boot.html
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationConfiguration.class)
@WebAppConfiguration
public class MailServiceTest {

    private Wiser wiser;

    @Before
    public void setUp() throws Exception {
        wiser = new Wiser();
        wiser.start();
    }

    @After
    public void tearDown() throws Exception {
        wiser.stop();
    }

    @Test
    public void send() throws Exception {
        new MailService().send("someone@localhosts", "someone@localhost", "Lorem ipsum", "Lorem ipsum dolor sit amet [...]");
        WiserAssertions.assertReceivedMessage(wiser)
                .from("someone@localhosts")
                .to("someone@localhost")
                .withSubject("Lorem ipsum")
                .withContent("Lorem ipsum dolor sit amet [...]");
    }
}
