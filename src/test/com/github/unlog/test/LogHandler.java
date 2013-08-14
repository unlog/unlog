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

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogHandler extends Handler {
    private LoggerFixture.LogReceiver logReceiver;

    public LogHandler(LoggerFixture.LogReceiver logReceiver) {
        this.logReceiver = logReceiver;
    }

    @Override
    public void publish(LogRecord record) {
        logReceiver.log(record.getLevel(), record.getMessage(), record.getThrown());
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws SecurityException {
        throw new UnsupportedOperationException();
    }

}
