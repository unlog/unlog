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
import com.github.unlog.LogFormat;
import com.github.unlog.LogLevel;

public class LogEvent {
    private final LogCategory logCategoryName;
    private final LogLevel logLevel;
    private final LogFormat message;
    private final Arguments arguments;

    public LogEvent(LogCategory logCategoryName, LogLevel logLevel, LogFormat messageFormat, Arguments arguments) {
        this.logCategoryName = logCategoryName;
        this.logLevel = logLevel;
        this.message = messageFormat;
        this.arguments = arguments;
    }

    public LogCategory getLogCategory() {
        return logCategoryName;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public LogFormat getMessage() {
        return message;
    }

    public Object[] getArgs() {
        return arguments.argsAsArray();
    }

    public Throwable throwableArg() {
        return arguments.throwableArg();
    }

    public String getFormattedMessage() {
        return message.format(arguments);
    }
}
