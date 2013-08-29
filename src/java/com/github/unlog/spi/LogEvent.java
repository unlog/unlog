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

package com.github.unlog.spi;

import com.github.unlog.LogCategory;
import com.github.unlog.LogLevel;
import com.github.unlog.LogMessage;

public class LogEvent {
    private final LogCategory logCategoryName;
    private final LogLevel logLevel;
    public final LogMessage message;

    public LogEvent(LogCategory logCategoryName, LogLevel logLevel, LogMessage message) {
        this.logCategoryName = logCategoryName;
        this.logLevel = logLevel;
        this.message = message;
    }

    public static LogEvent createLogEvent(LogCategory logCategoryName, LogLevel logLevel, LogMessage message) {
        return new LogEvent(logCategoryName, logLevel, message);
    }

    public LogCategory getLogCategory() {
        return logCategoryName;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public Object[] getArgs() {
        return args().argsAsArray();
    }

    private Arguments args() {
        return message.arguments();
    }

    public Throwable throwableArg() {
        return args().throwableArg();
    }

    public String getFormattedMessage() {
        return message.getFormattedMessage();
    }
}
