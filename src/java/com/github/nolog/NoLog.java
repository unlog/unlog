/*
 * Copyright 2013 Aaron Knauf
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nolog;

import com.github.nolog.jul.JavaUtilLogWriter;
import com.github.nolog.spi.Arguments;
import com.github.nolog.spi.LogEvent;
import com.github.nolog.spi.LogWriter;

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
