package adk.nolog.jul;

import adk.nolog.spi.EventLogger;
import adk.nolog.spi.LogEvent;
import adk.nolog.spi.LogWriter;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class JavaUtilLogWriter implements LogWriter, EventLogger {

    private final JavaUtilLoggingLevelMap levelMap = new JavaUtilLoggingLevelMap();

    @Override
    public void writeLogEvent(LogEvent logEvent) {
        Logger logger = Logger.getLogger(logEvent.getLogCategoryName());
        Level julLevel = logEvent.getLogLevel().mapLevel(levelMap);
        LogRecord logRecord = new LogRecord(julLevel, logEvent.getMessage());
        Object[] detail = logEvent.getArgs();


        if (logEvent.hasThrowableArg()) {
            logRecord.setThrown(logEvent.throwableArg());
            detail = logEvent.removeThrowableArg();
        }

        logRecord.setParameters(detail);
        logger.log(logRecord);
    }

}