package adk.nolog.spi;

import adk.nolog.LogLevel;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LogEvent {
    private final String logCategoryName;
    private final LogLevel logLevel;
    private final String message;
    private final Object[] args;

    public LogEvent(String logCategoryName, LogLevel logLevel, String message, ArgumentDescriber argumentDescriber) {
        this.logCategoryName = logCategoryName;
        this.logLevel = logLevel;
        this.message = message;
        this.args = argumentDescriber.args();
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

    public boolean hasArgs() {
        return getArgs() != null;
    }

    public Throwable throwableArg() {
        if (hasArgs()) {
            for (Object arg : getArgs()) {
                if (arg instanceof Throwable) return (Throwable) arg;
            }
        }
        return null;
    }

    public boolean hasThrowableArg() {
        //noinspection ThrowableResultOfMethodCallIgnored
        return throwableArg() != null;
    }

    public Object[] removeThrowableArg() {
        if (!hasArgs()) return null;

        Object[] args = getArgs();
        if (args.length == 1) return null;

        List<Object> argsList = new LinkedList<Object>(Arrays.asList(args));
        argsList.remove(throwableArg());
        return argsList.toArray(new Object[argsList.size()]);
    }
}
