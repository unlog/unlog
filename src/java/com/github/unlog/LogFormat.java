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

import com.github.unlog.internal.format.Joiner;
import com.github.unlog.spi.Arguments;

public class LogFormat {
    private final String format;

    public LogFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return format;
    }

    public String format(Arguments arguments) {
        Joiner argJoiner = new Joiner(", ");
        arguments.foreach(argJoiner);

        Joiner messageJoiner = new Joiner(": ");
        messageJoiner.element(format);
        if (!arguments.isEmpty()){
            messageJoiner.element(argJoiner);
        }
        return messageJoiner.toString();
    }

}
