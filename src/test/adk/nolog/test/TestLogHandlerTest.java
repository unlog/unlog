package adk.nolog.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestLogHandlerTest {

    @Rule
    public JUnitRuleMockery mockery = new JUnitRuleMockery();

    @Test
    public void shouldWriteJavaUtilLogOutputToLogDestination() {
        final TestLogHandler.LogReceiver logReceiver = mockery.mock(TestLogHandler.LogReceiver.class);
        TestLogHandler.configureLogger("shouldWriteJavaUtilLogOutputToLogDestination", logReceiver);

        mockery.checking(new Expectations() {{
            oneOf(logReceiver).log(Level.SEVERE, "Some log message");
        }});

        Logger logger = Logger.getLogger("shouldWriteJavaUtilLogOutputToLogDestination");
        logger.severe("Some log message");
    }

}
