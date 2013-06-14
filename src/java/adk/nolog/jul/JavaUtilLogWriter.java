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
        logRecord.setThrown(logEvent.throwableArg());
        logRecord.setParameters(logEvent.getArgs());

        logger.log(logRecord);
    }

}