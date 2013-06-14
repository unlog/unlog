package adk.nolog.spi;

import adk.nolog.LogLevel;

public class LogEvent {
    private final String logCategoryName;
    private final LogLevel logLevel;
    private final String message;
    private final Object[] args;

    public LogEvent(String logCategoryName, LogLevel logLevel, String message, Object[] args) {
        this.logCategoryName = logCategoryName;
        this.logLevel = logLevel;
        this.message = message;
        this.args = args;
    }

    public String getLogCategoryName() {
        return logCategoryName;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public String getMessage() {
        return message;
    }

    public Object[] getArgs() {
        return args;
    }
}
