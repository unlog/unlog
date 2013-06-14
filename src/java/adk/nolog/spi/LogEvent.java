package adk.nolog.spi;

import adk.nolog.LogLevel;

public class LogEvent {
    private final String logCategoryName;
    private final LogLevel logLevel;
    private final String message;
    private final ArgumentDescriber argumentDescriber;

    public LogEvent(String logCategoryName, LogLevel logLevel, String message, ArgumentDescriber argumentDescriber) {
        this.logCategoryName = logCategoryName;
        this.logLevel = logLevel;
        this.message = message;
        this.argumentDescriber = argumentDescriber;
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
        return argumentDescriber.args().toArray();
    }

    public Throwable throwableArg() {
        return argumentDescriber.throwable();
    }

}
