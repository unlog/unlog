package adk.nolog;

import adk.nolog.jul.JavaUtilLogWriter;
import adk.nolog.spi.Arguments;
import adk.nolog.spi.LogEvent;
import adk.nolog.spi.LogWriter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class NoLog {

    private static final LogWriter LOG_WRITER = new JavaUtilLogWriter();

    public static <L> L createLogger(Class<L> loggerInterface) {

        return createLogger(loggerInterface, null);
    }

    private static <L> L createLogger(Class<L> loggerInterface, Arguments context) {
        //noinspection unchecked
        return (L) Proxy.newProxyInstance(NoLog.class.getClassLoader(), new Class[]{loggerInterface}, new LogInvocationHandler());
    }

    private static String message(Method method) {
        return method.getName();
    }

    private static String categoryName(Method method) {
        return method.getDeclaringClass().getCanonicalName();
    }

    private static LogLevel determineLogLevel(Method method) {
        LogLevel logLevel;
        if (method.isAnnotationPresent(Log.class)) {
            logLevel = method.getAnnotation(Log.class).level();
        } else {
            logLevel = useDefaultLogLevel();
        }
        return logLevel;
    }

    private static LogLevel useDefaultLogLevel() {
        return LogLevel.DEBUG;
    }

    private static class LogInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Arguments arguments = new Arguments(args);
            LOG_WRITER.writeLogEvent(new LogEvent(categoryName(method), determineLogLevel(method), message(method), arguments));

            if (!Void.TYPE.equals(method.getReturnType())) {
                return createLogger(method.getReturnType(), arguments);
            }

            return null;
        }
    }
}
