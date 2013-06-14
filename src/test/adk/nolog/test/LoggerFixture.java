package adk.nolog.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerFixture implements TestRule {
    private final Logger logger;
    private final LogHandler handler;
    private final Level initialLogLevel;
    private LogReceiver logReceiver;
    private JUnitRuleMockery mockery;

    public LoggerFixture(Logger logger, JUnitRuleMockery mockery) {
        this.logger = logger;
        initialLogLevel = logger.getLevel();
        this.mockery = mockery;
        this.logReceiver = mockery.mock(LogReceiver.class);
        handler = new LogHandler(this.logReceiver);

        logger.setUseParentHandlers(false);
        logger.addHandler(handler);
    }

    public static LoggerFixture createLoggerFixture(JUnitRuleMockery mockery, String loggerName) {
        return new LoggerFixture(Logger.getLogger(loggerName), mockery);
    }


    public void setLevel(Level level) {
        logger.setLevel(level);
    }

    private void removeHandlerFromLogger() {
        logger.removeHandler(handler);
    }

    public void expectLogStatement(Level level, String message) {
        expectLogStatement(level, message, noArgs());
    }

    public void expectLogStatement(Level level, String message, Object[] args) {
        expectLogStatement(level, message, null, args);
    }

    public void expectLogStatement(Level level, String message, Exception t) {
        expectLogStatement(level, message, t, noArgs());
    }

    public void expectLogStatement(Level level, String message, Object[] detail, Exception e) {
        expectLogStatement(level, message, e, detail);
    }

    private void expectLogStatement(Level level, String message, Throwable t, Object[] detail) {
        Expectations expectations = new Expectations();
        expectations.oneOf(logReceiver).log(level, message, t, detail);
        mockery.checking(expectations);
    }

    private Object[] noArgs() {
        return new Object[0];
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } finally {
                    removeHandlerFromLogger();
                    resetLogLevel();
                }
            }
        };
    }

    private void resetLogLevel() {
        logger.setLevel(initialLogLevel);
    }

    public interface LogReceiver {
        public void log(Level level, String message, Throwable t, Object[] detail);
    }
}
