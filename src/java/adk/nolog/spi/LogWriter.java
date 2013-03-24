package adk.nolog.spi;

import adk.nolog.LogLevel;

public interface LogWriter {
    void writeLogEvent(String logCategoryName, LogLevel logLevel, String message, Object[] args);
}
