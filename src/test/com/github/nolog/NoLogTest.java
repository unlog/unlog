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

package com.github.nolog;


import com.github.nolog.test.LoggerFixture;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static com.github.nolog.LogLevel.*;
import static java.util.logging.Level.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class NoLogTest {

    @Rule
    public JUnitRuleMockery mockery = new JUnitRuleMockery();

    @Rule
    public LoggerFixture loggerFixture = LoggerFixture.createLoggerFixture(mockery, TestLogger.class.getCanonicalName());

    private TestLogger log;

    @Rule
    public LoggerFixture constructionProjectLoggerFixture = LoggerFixture.createLoggerFixture(mockery, ConstructionProject.class.getCanonicalName());

    @Before
    public void setUp() throws Exception {
        log = NoLog.createLogger(TestLogger.class);
    }

    @Test
    public void shouldWriteLogInterfaceMethodNameToJavaUtilLoggingAppender() {

        loggerFixture.setLevel(FINEST);
        loggerFixture.expectLogStatement(FINEST, "somethingHappened");

        log.somethingHappened();
    }

    @Test
    public void shouldOnlyLogAtLevelSpecifiedInAnnotation() {

        loggerFixture.setLevel(SEVERE);
        loggerFixture.expectLogStatement(SEVERE, "someSortOfErrorOccurred");

        log.someSortOfErrorOccurred();
        log.somethingHappened();
    }

    @Test
    public void shouldLogArguments() {
        loggerFixture.setLevel(FINEST);
        loggerFixture.expectLogStatement(FINEST, "somethingHappenedAndHeresThe", new Object[]{"detail"});

        log.somethingHappenedAndHeresThe("detail");
    }

    @Test
    public void shouldLogExceptionsUsingTheUnderlyingFrameworkFacility() {
        loggerFixture.setLevel(FINEST);
        Exception e = new Exception();
        loggerFixture.expectLogStatement(FINEST, "ohNoTheresBeenAnException", e);

        log.ohNoTheresBeenAnException(e);
    }

    @Test
    public void shouldLogExceptionsAlongWithOtherArguments() {
        loggerFixture.setLevel(FINEST);
        Exception e = new Exception();
        loggerFixture.expectLogStatement(FINEST, "ohNoTheresBeenAnException", new Object[]{"while processing some transaction"}, e);

        log.ohNoTheresBeenAnException("while processing some transaction", e);
    }

    @Test
    @Ignore
    public void shouldLogTransactionContextAlongWithTransactionEvents() {

        loggerFixture.setLevel(FINEST);

        Customer forThisCustomer = new Customer("Bob");
        String atThisAddress = "at this address";
        Object[] expectedContext = {atThisAddress, forThisCustomer};
        loggerFixture.expectLogStatement(FINEST, "startedBuildingAHouse", expectedContext);

        ConstructionProject house = log.startedBuildingAHouse(atThisAddress, forThisCustomer);

        assertThat(house, not(nullValue()));

        constructionProjectLoggerFixture.setLevel(FINEST);
        constructionProjectLoggerFixture.expectLogStatement(FINEST, "roofComplete", expectedContext);

        house.roofComplete();
    }

    public interface TestLogger {
        void somethingHappened();

        @Log(level = ERROR)
        void someSortOfErrorOccurred();

        void somethingHappenedAndHeresThe(String detail);

        void ohNoTheresBeenAnException(Exception e);

        void ohNoTheresBeenAnException(String details, Exception e);

        ConstructionProject startedBuildingAHouse(String address, Customer customer);
    }

    public class Customer {
        public Customer(String name) {
        }
    }

    public interface ConstructionProject {
        void roofComplete();
    }
}
