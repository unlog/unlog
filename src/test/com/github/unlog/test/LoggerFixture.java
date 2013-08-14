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

package com.github.unlog.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerFixture implements TestRule {
    private final Logger logger;
    private final LogHandler handler;
    private final Level initialLogLevel;
    private LogReceiver logReceiver;
    private JUnitRuleMockery mockery;

    public LoggerFixture(Logger logger, JUnitRuleMockery mockery, String loggerName) {
        this.logger = logger;
        initialLogLevel = logger.getLevel();
        this.mockery = mockery;
        this.logReceiver = mockery.mock(LogReceiver.class, loggerName);
        handler = new LogHandler(this.logReceiver);

        logger.setUseParentHandlers(false);
        logger.addHandler(handler);
    }

    public static LoggerFixture createLoggerFixture(JUnitRuleMockery mockery, String loggerName) {
        return new LoggerFixture(Logger.getLogger(loggerName), mockery, loggerName);
    }


    public void setLevel(Level level) {
        logger.setLevel(level);
    }

    private void removeHandlerFromLogger() {
        logger.removeHandler(handler);
    }

    public void expectLogStatement(Level level, String message) {
        expectLogStatement(level, message, null);
    }

    public void expectLogStatement(Level level, String message, Throwable t) {
        Expectations expectations = new Expectations();
        expectations.oneOf(logReceiver).log(level, message, t);
        mockery.checking(expectations);
    }

    private Object[] noArgs() {
        return new Object[0];
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } finally {
                    removeHandlerFromLogger();
                    resetLogLevel();
                }
            }
        };
    }

    private void resetLogLevel() {
        logger.setLevel(initialLogLevel);
    }

    public interface LogReceiver {
        public void log(Level level, String message, Throwable t);
    }
}
