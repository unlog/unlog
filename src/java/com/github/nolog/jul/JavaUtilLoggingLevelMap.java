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

package com.github.nolog.jul;

import com.github.nolog.spi.LevelMap;

import java.util.logging.Level;

public class JavaUtilLoggingLevelMap implements LevelMap<Level> {

    @Override
    public java.util.logging.Level mapError() {
        return java.util.logging.Level.SEVERE;
    }

    @Override
    public Level mapDebug() {
        return Level.FINEST;
    }

    @Override
    public Level mapInfo() {
        return Level.INFO;
    }

    @Override
    public Level mapWarning() {
        return Level.WARNING;
    }
}
