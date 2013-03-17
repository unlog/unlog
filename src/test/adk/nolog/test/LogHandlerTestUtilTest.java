package adk.nolog.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHandlerTestUtilTest {

    @Rule
    public JUnitRuleMockery mockery = new JUnitRuleMockery();

    @Test
    public void shouldWriteJavaUtilLogOutputToLogDestination() {
        final LoggerFixture loggerFixture = LoggerFixture.createLoggerFixture(mockery, "shouldWriteJavaUtilLogOutputToLogDestination");

        mockery.checking(new Expectations() {{
            oneOf(loggerFixture.getLogReceiver()).log(Level.SEVERE, "Some log message");
        }});

        Logger logger = Logger.getLogger("shouldWriteJavaUtilLogOutputToLogDestination");
        logger.severe("Some log message");
    }

}
