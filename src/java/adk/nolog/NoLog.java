package adk.nolog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Logger;

public class NoLog {
    public static <L> L createLogger(Class<L> loggerInterface) {

        //noinspection unchecked
        return (L) Proxy.newProxyInstance(NoLog.class.getClassLoader(), new Class[]{loggerInterface}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Logger logger = Logger.getLogger(method.getDeclaringClass().getCanonicalName());
                logger.finest(method.getName());
                return null;
            }
        });
    }
}
