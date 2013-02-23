package adk.nolog.test;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class TestLogHandler extends Handler {
    private LogReceiver logReceiver;

    public TestLogHandler(LogReceiver logReceiver) {
        this.logReceiver = logReceiver;
    }

    @Override
    public void publish(LogRecord record) {

        logReceiver.log(record.getLevel(), record.getMessage());
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws SecurityException {
        throw new UnsupportedOperationException();
    }

    public static Logger configureLogger(String loggerName, LogReceiver logReceiver) {
        Logger logger = Logger.getLogger(loggerName);
        logger.setUseParentHandlers(false);
        logger.addHandler(new TestLogHandler(logReceiver));
        return logger;
    }

    public interface LogReceiver {
        public void log(Level level, String message);
    }
}
