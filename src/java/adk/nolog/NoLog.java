package adk.nolog;

import adk.nolog.jul.JavaUtilLoggingLevelMap;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NoLog {

    public static <L> L createLogger(Class<L> loggerInterface) {

        //noinspection unchecked
        return (L) Proxy.newProxyInstance(NoLog.class.getClassLoader(), new Class[]{loggerInterface}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                writeLogEvent(method, args, method.getDeclaringClass().getCanonicalName(), method.getName());
                return null;
            }
        });
    }

    private static void writeLogEvent(Method method, Object[] args, String logCategory, String message) {
        Logger logger = Logger.getLogger(logCategory);
        logger.log(determineLogLevel(method), message, args);
    }

    private static Level determineLogLevel(Method method) {
        adk.nolog.Level level;
        if (method.isAnnotationPresent(Log.class)) {
            level = determineLogLevelFromAnnotation(method.getAnnotation(Log.class));
        } else {
            level = useDefaultLogLevel();
        }
        return level.mapLevel(new JavaUtilLoggingLevelMap());
    }

    private static adk.nolog.Level useDefaultLogLevel() {
        return adk.nolog.Level.DEBUG;
    }

    private static adk.nolog.Level determineLogLevelFromAnnotation(Log logAnnotation) {
        return logAnnotation.level();
    }

}
