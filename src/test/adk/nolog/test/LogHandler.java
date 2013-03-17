package adk.nolog.test;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogHandler extends Handler {
    private LoggerFixture.LogReceiver logReceiver;

    public LogHandler(LoggerFixture.LogReceiver logReceiver) {
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

}
