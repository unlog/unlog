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

package com.github.unlog.internal;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LogFormatFactoryTest {
    @Test
    public void shouldUseCamelCaseToSplitMethodNameIntoWords() {
        assertThat(new LogFormatFactory().logFormat(new LogMethod<SomeLog>(SomeLog.class) {{
            forMethod().somethingHappened();
        }}.getMethod()).toString(), is("Something happened"));
    }

    public interface SomeLog {
        void somethingHappened();
    }

    private class LogMethod<T> {
        private final Class<T> logClass;
        private Method logMethod;

        public LogMethod(Class<T> logClass) {
            this.logClass = logClass;
        }

        public T forMethod(){
            //noinspection unchecked
            return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{logClass}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    logMethod = method;
                    return null;
                }
            });
        }

        public Method getMethod() {
            return logMethod;
        }
    }
}
