package de.helfenkannjeder.come2help.server.util.mail;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import de.helfenkannjeder.come2help.server.cucumber.configuration.TestApplicationConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

/**
 * based on http://www.javavillage.in/greenmail-java-mail.php
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationConfiguration.class)
@WebAppConfiguration
public class MailServiceTest {


   private GreenMail testSmtp;

    @Before
    public void testSmtpInit(){
        ServerSetup setup = new ServerSetup(25, "localhost", "smtp");
        testSmtp = new GreenMail(setup);
        testSmtp.start();
    }

    @After
    public void cleanup(){
        testSmtp.stop();
    }

    @Test
    public void send() throws Exception {
        new MailService().send("someone@localhosts", "someone@localhost", "Lorem ipsum", "Lorem ipsum dolor sit amet [...]");
        javax.mail.Message[] messages = testSmtp.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("Lorem ipsum", messages[0].getSubject());
        String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
        assertEquals("Lorem ipsum dolor sit amet [...]", body);
    }
}
