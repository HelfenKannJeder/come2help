package de.helfenkannjeder.come2help.server;

import de.helfenkannjeder.come2help.server.configuration.Come2helpApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Come2helpApplication.class)
@WebAppConfiguration
public class Come2helpSrvApplicationTests {

    @Test
    public void contextLoads() {
    }

}
