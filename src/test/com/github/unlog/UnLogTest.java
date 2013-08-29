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


import com.github.unlog.test.LoggerFixture;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.unlog.LogLevel.*;
import static java.util.logging.Level.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class UnLogTest {

    @Rule
    public JUnitRuleMockery mockery = new JUnitRuleMockery();

    @Rule
    public LoggerFixture loggerFixture = LoggerFixture.createLoggerFixture(mockery, TestLogger.class.getCanonicalName());

    private TestLogger log;

    @Rule
    public LoggerFixture constructionProjectLoggerFixture = LoggerFixture.createLoggerFixture(mockery, ConstructionProjectLog.class.getCanonicalName());

    @Before
    public void setUp() throws Exception {
        log = UnLog.createLogger(TestLogger.class);
    }

    @Test
    public void shouldWriteLogInterfaceMethodNameSplitIntoCamelCaseWordsToJavaUtilLoggingAppender() {

        loggerFixture.setLevel(FINEST);
        loggerFixture.expectLogStatement(FINEST, "Something happened");

        log.somethingHappened();
    }

    @Test
    public void shouldOnlyLogAtLevelSpecifiedInAnnotation() {

        loggerFixture.setLevel(SEVERE);
        loggerFixture.expectLogStatement(SEVERE, "Some sort of error occurred");

        log.someSortOfErrorOccurred();
        log.somethingHappened();
    }

    @Test
    public void shouldLogArguments() {
        loggerFixture.setLevel(FINEST);
        loggerFixture.expectLogStatement(FINEST, "Something happened and heres the: detailed, info");

        log.somethingHappenedAndHeresThe("detailed", "info");
    }

    @Test
    public void shouldLogExceptionsUsingTheUnderlyingFrameworkFacility() {
        loggerFixture.setLevel(FINEST);
        Exception e = new Exception();
        loggerFixture.expectLogStatement(FINEST, "Oh no theres been an exception", e);

        log.ohNoTheresBeenAnException(e);
    }

    @Test
    public void shouldLogExceptionsAlongWithOtherArguments() {
        loggerFixture.setLevel(FINEST);
        Exception e = new Exception();
        loggerFixture.expectLogStatement(FINEST, "Oh no theres been an exception: while processing some transaction", e);

        log.ohNoTheresBeenAnException("while processing some transaction", e);
    }

    @Test
    public void shouldLogTransactionContextAlongWithTransactionEvents() {

        Customer forThisCustomer = new Customer("Bob");
        String atThisAddress = "at this address";
        ConstructionProjectLog constructionLog = log.constructionJobFor(forThisCustomer, atThisAddress);

        assertThat(constructionLog, not(nullValue()));

        constructionProjectLoggerFixture.setLevel(FINEST);
        constructionProjectLoggerFixture.expectLogStatement(FINEST, "Construction job for: Bob, " +
                "at this address - Roof complete");

        constructionLog.roofComplete();
    }

    public interface TestLogger {
        void somethingHappened();

        @Log(level = ERROR)
        void someSortOfErrorOccurred();

        void somethingHappenedAndHeresThe(String detail, String moreDetail);

        void ohNoTheresBeenAnException(Exception e);

        void ohNoTheresBeenAnException(String details, Exception e);

        ConstructionProjectLog constructionJobFor(Customer customer, String address);
    }

    public class Customer {
        private final String name;

        public Customer(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public interface ConstructionProjectLog {
        void roofComplete();
    }
}
