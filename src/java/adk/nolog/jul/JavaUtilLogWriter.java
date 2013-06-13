package adk.nolog.jul;

import adk.nolog.LogLevel;
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
    public void writeLogEvent(String logCategoryName, LogLevel logLevel, String message, Object[] args) {
        Logger logger = Logger.getLogger(logCategoryName);
        Level julLevel = logLevel.mapLevel(levelMap);
        LogRecord logRecord = new LogRecord(julLevel, message);
        Object[] detail = args;
        if (hasThrowableArg(args)) {
            logRecord.setThrown(throwableArg(args));
            detail = removeThrowableArg(args);
        }
        logRecord.setParameters(detail);
        logger.log(logRecord);
    }

    private Object[] removeThrowableArg(Object[] args) {
        if (args == null) return null;
        if (args.length == 1) return null;

        List<Object> argsList = new LinkedList<Object>(Arrays.asList(args));

        argsList.remove(throwableArg(args));
        return argsList.toArray(new Object[argsList.size()]);
    }

    private Throwable throwableArg(Object[] args) {
        if (hasArgs(args)) {
            for (Object arg : args) {
                if (arg instanceof Throwable) return (Throwable) arg;
            }
        }
        return null;
    }

    private boolean hasThrowableArg(Object[] args) {
        if (hasArgs(args)) {
            for (Object arg : args) {
                if (arg instanceof Throwable) return true;
            }
        }
        return false;
    }

    private boolean hasArgs(Object[] args) {
        return args != null;
    }
}
