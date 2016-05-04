package de.helfenkannjeder.come2help.server.rest.logging;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LogSessionTest {

    @Test
    public void singleKeyValueLogEntryIsSerialized() throws Exception {
        LogSession.start();
        LogSession.log("key", "value");
        String logMessage = LogSession.getLogMessage();

        assertThat(logMessage, is("key=value"));
    }

    @Test
    public void unstartedLogSessionHasEmptyLogMessage() throws Exception {
        LogSession.log("key", "value");
        String logMessage = LogSession.getLogMessage();

        assertThat(logMessage, is(""));
    }
}