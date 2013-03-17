package adk.nolog;

import adk.nolog.jul.JavaUtilLoggingLevelMap;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NoLog {

    static final Level DEFAULT_LOG_LEVEL = Level.FINEST;

    public static <L> L createLogger(Class<L> loggerInterface) {

        //noinspection unchecked
        return (L) Proxy.newProxyInstance(NoLog.class.getClassLoader(), new Class[]{loggerInterface}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Logger logger = Logger.getLogger(method.getDeclaringClass().getCanonicalName());
                logger.log(determineLogLevel(method), method.getName());
                return null;
            }
        });
    }

    private static Level determineLogLevel(Method method) {
        if (method.isAnnotationPresent(Log.class)) {
            Log logAnnotation = method.getAnnotation(Log.class);
            return determineLogLevelFromAnnotation(logAnnotation);
        } else {
            return DEFAULT_LOG_LEVEL;
        }
    }

    private static Level determineLogLevelFromAnnotation(Log logAnnotation) {
        adk.nolog.Level level = logAnnotation.level();
        return level.mapLevel(new JavaUtilLoggingLevelMap());
    }

}
