package adk.nolog;


import adk.nolog.test.LogHandlerTestUtil;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NoLogTest {

    @Rule public JUnitRuleMockery mockery = new JUnitRuleMockery();

    @Test
    public void shouldWriteLogInterfaceMethodNameToJavaUtilLoggingAppender() {
        final TestLogger log = NoLog.createLogger(TestLogger.class);

        final LogHandlerTestUtil.LogReceiver logReceiver = mockery.mock(LogHandlerTestUtil.LogReceiver.class);
        Logger logger = LogHandlerTestUtil.configureLogger("adk.nolog.NoLogTest.TestLogger", logReceiver);
        logger.setLevel(Level.FINEST);
        mockery.checking(new Expectations(){{
            oneOf(logReceiver).log(Level.FINEST, "somethingHappened");
        }});

        log.somethingHappened();

    }

    public interface TestLogger {
        void somethingHappened();
    }

}
