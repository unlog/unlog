package adk.nolog.jul;

import adk.nolog.LogLevel;
import adk.nolog.spi.LogWriter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaUtilLogWriter implements LogWriter {

    private final JavaUtilLoggingLevelMap levelMap = new JavaUtilLoggingLevelMap();

    @Override
    public void writeLogEvent(String logCategoryName, LogLevel logLevel, String message, Object[] args) {
        Logger logger = Logger.getLogger(logCategoryName);
        Level level = logLevel.mapLevel(levelMap);
        if (hasThrowableArg(args)) {
            logger.log(level, message, throwableArg(args));
        } else {
            logger.log(level, message, args);
        }
    }

    private Throwable throwableArg(Object[] args) {
        return (Throwable) args[0];
    }

    private boolean hasThrowableArg(Object[] args) {
        return hasArgs(args) && args[0] instanceof Throwable;
    }

    private boolean hasArgs(Object[] args) {
        return args != null;
    }
}
