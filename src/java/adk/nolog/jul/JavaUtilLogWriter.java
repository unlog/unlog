package adk.nolog.jul;

import adk.nolog.spi.LogEvent;
import adk.nolog.spi.LogWriter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class JavaUtilLogWriter implements LogWriter {

    private final JavaUtilLoggingLevelMap levelMap = new JavaUtilLoggingLevelMap();

    @Override
    public void writeLogEvent(LogEvent logEvent) {
        Logger logger = Logger.getLogger(logEvent.getLogCategoryName());
        Level julLevel = logEvent.getLogLevel().mapLevel(levelMap);
        LogRecord logRecord = new LogRecord(julLevel, logEvent.getMessage());
        Object[] detail = logEvent.getArgs();
        if (hasThrowableArg(logEvent)) {
            logRecord.setThrown(throwableArg(logEvent));
            detail = removeThrowableArg(logEvent);
        }
        logRecord.setParameters(detail);
        logger.log(logRecord);
    }

    private Object[] removeThrowableArg(LogEvent event) {
        if (!hasArgs(event)) return null;

        Object[] args = event.getArgs();
        if (args.length == 1) return null;

        List<Object> argsList = new LinkedList<Object>(Arrays.asList(args));
        argsList.remove(throwableArg(event));
        return argsList.toArray(new Object[argsList.size()]);
    }

    private Throwable throwableArg(LogEvent event) {
        if (hasArgs(event)) {
            for (Object arg : event.getArgs()) {
                if (arg instanceof Throwable) return (Throwable) arg;
            }
        }
        return null;
    }

    private boolean hasThrowableArg(LogEvent event) {
        return throwableArg(event) != null;
    }

    private boolean hasArgs(LogEvent event) {
        return event.getArgs() != null;
    }
}
