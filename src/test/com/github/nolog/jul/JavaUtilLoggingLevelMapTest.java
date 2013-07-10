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

import org.junit.Test;

import java.util.logging.Level;

import static com.github.nolog.LogLevel.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class JavaUtilLoggingLevelMapTest {

    @Test
    public void shouldMapAllLogLevels() {
        JavaUtilLoggingLevelMap levelMap = new JavaUtilLoggingLevelMap();
        assertThat(DEBUG.mapLevel(levelMap), is(Level.FINEST));
        assertThat(INFO.mapLevel(levelMap), is(Level.INFO));
        assertThat(WARNING.mapLevel(levelMap), is(Level.WARNING));
        assertThat(ERROR.mapLevel(levelMap), is(Level.SEVERE));
    }
}
