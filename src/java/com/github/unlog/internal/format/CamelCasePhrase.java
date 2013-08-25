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

import java.util.Iterator;
import java.util.LinkedList;

class CamelCasePhrase {

    private static final ParseState SeekingAcronymOrWord = new AbstractParseState() {

        @Override
        protected ParseState parseOther(char c, ElementBuilder builder) {
            builder.addCharToElement(c);
            return ParsingWord;
        }

        @Override
        protected ParseState parseUpperCase(char c, ElementBuilder builder) {
            builder.addCharToElement(c);
            return ParsingAcronym;
        }
    };
    private static final ParseState SeekingStartOfWord = new ParseState() {
        public ParseState parseNextCharacter(char c, ElementBuilder builder) {
            builder.addCharToElement(c);
            return SeekingAcronymOrWord;
        }
    };
    private static final ParseState ParsingAcronym = new AbstractParseState() {

        @Override
        protected ParseState parseLowerCase(char c, ElementBuilder builder) {
            char lastCharParsed = builder.removeLastChar();
            builder.endOfElement();
            return SeekingStartOfWord.parseNextCharacter(lastCharParsed,
                    builder).parseNextCharacter(c, builder);
        }
    };
    private static final ParseState ParsingWord = new AbstractParseState() {

        @Override
        protected ParseState parseUpperCase(char c, ElementBuilder builder) {
            builder.endOfElement();
            return SeekingStartOfWord.parseNextCharacter(c, builder);
        }
    };
    private final LinkedList<String> words = new LinkedList<String>();

    public CamelCasePhrase(String phrase) {
        ParseState state = SeekingStartOfWord;
        ElementBuilder elementBuilder = new ElementBuilder();
        for (int i = 0; i < phrase.length(); i++) {
            state = state.parseNextCharacter(phrase.charAt(i), elementBuilder);
        }

        elementBuilder.endOfPhrase();
    }

    public String toSentenceCase() {
        Iterator<String> iterator = words.iterator();
        StringBuilder sentencePhrase = new StringBuilder(ensureInitialCapital(iterator.next()));

        while (iterator.hasNext()) {
            String nextWord = iterator.next();
            sentencePhrase.append(' ').append(toSentenceCase(nextWord));
        }
        return sentencePhrase.toString();
    }

    private String toSentenceCase(String nextWord) {
        if (isAcronym(nextWord)) {
            return nextWord;
        }
        return nextWord.toLowerCase();
    }

    private boolean isAcronym(String word) {
        return word.length() > 1 && word.toUpperCase().equals(word);
    }

    private String ensureInitialCapital(String word) {
        if (word.isEmpty()) {
            return "";
        }
        StringBuilder wordToCapitalise = new StringBuilder(word);
        wordToCapitalise.setCharAt(0, Character.toUpperCase(wordToCapitalise.charAt(0)));
        return wordToCapitalise.toString();
    }


    class ElementBuilder {

        private StringBuilder currentElement = new StringBuilder();

        public void endOfElement() {
            words.add(currentElement.toString());
            currentElement.setLength(0);
        }

        public void addCharToElement(char c) {
            currentElement.append(c);
        }

        public void endOfPhrase() {
            words.add(currentElement.toString());
        }

        public char removeLastChar() {
            char lastChar = currentElement.charAt(currentElement.length() - 1);
            currentElement.deleteCharAt(currentElement.length() - 1);
            return lastChar;
        }
    }

}
