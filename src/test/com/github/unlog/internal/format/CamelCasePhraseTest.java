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

package com.github.unlog.internal.format;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class CamelCasePhraseTest {

    @Test
    public void emptyStringShouldParseToEmptyPhrase() {
        assertThat(new CamelCasePhrase("").toSentenceCase(), is(""));
    }
    @Test
    public void shouldConvertLowerCamelCasePhraseToSentenceCase() {

        assertThat(new CamelCasePhrase("One").toSentenceCase(), is("One"));
        assertThat(new CamelCasePhrase("one").toSentenceCase(), is("One"));
        assertThat(new CamelCasePhrase("twoWords").toSentenceCase(), is("Two words"));
        assertThat(new CamelCasePhrase("anotherThreeWords").toSentenceCase(), is("Another three words"));
    }

    @Test
    public void shouldTreatConsecutiveCapsAsAcronyms() {
        assertThat(new CamelCasePhrase("TLA").toSentenceCase(), is("TLA"));
        assertThat(new CamelCasePhrase("TLAcronym").toSentenceCase(), is("TL acronym"));
        assertThat(new CamelCasePhrase("ThreeLA").toSentenceCase(), is("Three LA"));
        assertThat(new CamelCasePhrase("TLetterA").toSentenceCase(), is("T letter a"));
    }
}
