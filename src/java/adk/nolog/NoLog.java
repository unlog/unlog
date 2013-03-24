package adk.nolog;

import adk.nolog.jul.JavaUtilLogWriter;
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
                LOG_WRITER.writeLogEvent(method.getDeclaringClass().getCanonicalName(), determineLogLevel(method), method.getName(), args);
                return null;
            }
        });
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
