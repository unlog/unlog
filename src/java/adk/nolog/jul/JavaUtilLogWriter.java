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

package adk.nolog.jul;

import adk.nolog.spi.EventLogger;
import adk.nolog.spi.LogEvent;
import adk.nolog.spi.LogWriter;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class JavaUtilLogWriter implements LogWriter, EventLogger {

    private final JavaUtilLoggingLevelMap levelMap = new JavaUtilLoggingLevelMap();

    @Override
    public void writeLogEvent(LogEvent logEvent) {
        Logger logger = Logger.getLogger(logEvent.getLogCategoryName());
        Level julLevel = logEvent.getLogLevel().mapLevel(levelMap);

        LogRecord logRecord = new LogRecord(julLevel, logEvent.getMessage());
        logRecord.setThrown(logEvent.throwableArg());
        logRecord.setParameters(logEvent.getArgs());

        logger.log(logRecord);
    }

}