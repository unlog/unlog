package adk.nolog;


import adk.nolog.test.LoggerFixture;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static adk.nolog.Level.ERROR;
import static java.util.logging.Level.FINEST;
import static java.util.logging.Level.SEVERE;

public class NoLogTest {

    @Rule
    public JUnitRuleMockery mockery = new JUnitRuleMockery();
    private TestLogger log;

    @Rule
    public LoggerFixture loggerFixture = LoggerFixture.createLoggerFixture(mockery, TestLogger.class.getCanonicalName());

    @Before
    public void setUp() throws Exception {
        log = NoLog.createLogger(TestLogger.class);
    }

    @Test
    public void shouldWriteLogInterfaceMethodNameToJavaUtilLoggingAppender() {

        loggerFixture.setLevel(FINEST);
        loggerFixture.expectLogStatement(FINEST, "somethingHappened");

        log.somethingHappened();
    }

    @Test
    public void shouldLogAtLevelSpecifiedInAnnotation() {

        loggerFixture.setLevel(SEVERE);
        loggerFixture.expectLogStatement(SEVERE, "logThisAtErrorLevel");

        log.logThisAtErrorLevel();
    }

    public interface TestLogger {
        void somethingHappened();

        @Log(level = ERROR)
        void logThisAtErrorLevel();
    }

}
