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
        logger.log(level, message, args);
    }
}
