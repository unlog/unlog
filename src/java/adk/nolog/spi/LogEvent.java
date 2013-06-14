package adk.nolog.spi;

import adk.nolog.LogLevel;

public class LogEvent {
    private final String logCategoryName;
    private final LogLevel logLevel;
    private final String message;
    private final Arguments arguments;

    public LogEvent(String logCategoryName, LogLevel logLevel, String message, Arguments arguments) {
        this.logCategoryName = logCategoryName;
        this.logLevel = logLevel;
        this.message = message;
        this.arguments = arguments;
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
        return arguments.args().toArray();
    }

    public Throwable throwableArg() {
        return arguments.throwableArg();
    }

}
