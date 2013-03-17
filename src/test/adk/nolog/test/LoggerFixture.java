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
    private LogReceiver logReceiver;
    private JUnitRuleMockery mockery;

    public LoggerFixture(Logger logger, JUnitRuleMockery mockery) {
        this.logger = logger;
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

    public LogReceiver getLogReceiver() {
        return logReceiver;
    }

    private void removeHandlerFromLogger() {
        logger.removeHandler(handler);
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
                removeHandlerFromLogger();
            }
        };
    }

    public interface LogReceiver {
        public void log(Level level, String message);
    }
}
