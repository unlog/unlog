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

package com.github.unlog;

import com.github.unlog.spi.Arguments;

public class LogMessage {
    public static final LogMessage EMPTY = new LogMessage() {
        @Override
        LogMessage extendWith(LogFormat format, Arguments arguments) {
            return new LogMessage(format, arguments);
        }
    };
    private final LogFormat format;
    private final Arguments arguments;

    public LogMessage(LogFormat format, Arguments arguments) {
        this.format = format;
        this.arguments = arguments;
    }

    private LogMessage() {
        this(new LogFormat(""), Arguments.NO_ARGS);
    }

    public String getFormattedMessage() {
        return format.format(arguments);
    }

    public Arguments arguments() {
        return arguments;
    }

    LogMessage extendWith(LogFormat format, Arguments arguments) {

        String prefix = getFormattedMessage();
        String suffix = format.toString();

        return new LogMessage(new LogFormat(prefix + " - " + suffix), arguments);
    }

}
