package adk.nolog.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerFixture implements TestRule{
    private final Logger value;
    private LogReceiver logReceiver;
    private JUnitRuleMockery mockery;

    public LoggerFixture(Logger value, LogReceiver logReceiver, JUnitRuleMockery mockery) {
        this.value = value;
        this.logReceiver = logReceiver;
        this.mockery = mockery;
    }

    public static LoggerFixture createLoggerFixture(JUnitRuleMockery mockery1, String loggerName) {
        final LogReceiver logReceiver = mockery1.mock(LogReceiver.class);
        Logger logger = Logger.getLogger(loggerName);
        logger.setUseParentHandlers(false);
        logger.addHandler(new LogHandler(logReceiver));
        return new LoggerFixture(logger, logReceiver, mockery1);
    }


    public Logger getValue() {
        return value;
    }

    public void setLevel(Level level) {
        Logger jLogger = getValue();
        jLogger.setLevel(level);
    }

    public LogReceiver getLogReceiver() {
        return logReceiver;
    }

    public void clearHandlersFromLogger() {
        for (Handler handler : getValue().getHandlers()) {
            getValue().removeHandler(handler);
        }
    }

    public void expectLogStatement(Level level, String message) {
        Expectations expectations = new Expectations();
        expectations.oneOf(logReceiver).log(level, message);
        mockery.checking(expectations);
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                base.evaluate();
                clearHandlersFromLogger();
            }
        };
    }

    public interface LogReceiver {
        public void log(Level level, String message);
    }
}
