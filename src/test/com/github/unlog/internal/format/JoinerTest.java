package com.github.unlog.internal.format;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JoinerTest {

    @Test
    public void shouldAppendSingleElementWithoutSeparator() throws Exception {
        String joined = new Joiner(",").element("single").toString();
        assertThat(joined, is("single"));
    }

    @Test
    public void shouldSeparateMultipleArgsWithSeparator() throws Exception {
        String joined = new Joiner(",").element("first").element("second").toString();
        assertThat(joined, is("first,second"));
    }
}
