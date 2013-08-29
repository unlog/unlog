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

import org.junit.Test;

import static com.github.unlog.spi.Arguments.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LogMessageTest {

    @Test
    public void shouldConcatenateCurrentMessageWithAnotherMessage() {
        LogMessage messageWithContext = new LogMessage(new LogFormat("Message"), args(1))
                .extendWith(new LogFormat("more message"), args(2));
        assertThat(messageWithContext.getFormattedMessage(), is("Message: 1 - more message: 2"));

    }

    @Test
    public void topLevelMessagesShouldLogWithoutContext() {
        LogMessage messageWithoutContext = LogMessage.EMPTY.extendWith(new LogFormat("some message"), args());
        assertThat(messageWithoutContext.getFormattedMessage(), is("some message"));
    }
}
