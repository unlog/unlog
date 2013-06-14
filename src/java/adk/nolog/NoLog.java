package adk.nolog;

import adk.nolog.jul.JavaUtilLogWriter;
import adk.nolog.spi.ArgumentDescriber;
import adk.nolog.spi.LogEvent;
import adk.nolog.spi.LogWriter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class NoLog {

    private static final LogWriter LOG_WRITER = new JavaUtilLogWriter();

    public static <L> L createLogger(Class<L> loggerInterface) {

        //noinspection unchecked
        return (L) Proxy.newProxyInstance(NoLog.class.getClassLoader(), new Class[]{loggerInterface}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                LOG_WRITER.writeLogEvent(new LogEvent(categoryName(method), determineLogLevel(method), message(method), new ArgumentDescriber(args)));
                return null;
            }
        });
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

}
