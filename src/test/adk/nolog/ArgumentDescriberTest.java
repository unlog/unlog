package adk.nolog;

import adk.nolog.spi.ArgumentDescriber;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class ArgumentDescriberTest {

    @Test
    public void shouldHandleNoArgs() {
        ArgumentDescriber argumentDescriber = givenAnArgumentDescriber().withNoArgs().build();
        assertThat(argumentDescriber.args(), is(not(nullValue())));
        assertThat(argumentDescriber.args().size(), is(0));
        assertThat(argumentDescriber.throwable(), is(nullValue()));
    }

    @Test
    public void shouldHandleOneArg() throws NoSuchMethodException {
        ArgumentDescriber argumentDescriber = givenAnArgumentDescriber().withOneArg().build();
        assertThat(argumentDescriber.args().size(), is(1));
        assertThat(argumentDescriber.throwable(), is(nullValue()));
    }

    @Test
    public void shouldHandleMultipleArgs() throws NoSuchMethodException {
        ArgumentDescriber argumentDescriber = givenAnArgumentDescriber().withMultipleArgs().build();
        assertThat(argumentDescriber.args().size(), is(greaterThan(1)));
        assertThat(argumentDescriber.throwable(), is(nullValue()));
    }

    @Test
    public void shouldHandleNoArgsAndAnException() throws NoSuchMethodException {
        ArgumentDescriber argumentDescriber = givenAnArgumentDescriber().withAnException().build();
        assertThat(argumentDescriber.args().size(), is(0));
        assertThat(argumentDescriber.throwable(), is(not(nullValue())));
    }

    @Test
    public void shouldHandleArgAndException() {
        ArgumentDescriber argumentDescriber = givenAnArgumentDescriber().withArgAndException().build();
        assertThat(argumentDescriber.args().size(), is(1));
        assertThat(argumentDescriber.throwable(), is(not(nullValue())));
    }


    private ArgumentDescriberFixture givenAnArgumentDescriber() {
        return new ArgumentDescriberFixture();
    }


    private class ArgumentDescriberFixture {

        private Object[] args;

        private ArgumentDescriber build() {
            return new ArgumentDescriber(args);
        }

        private ArgumentDescriberFixture withNoArgs() {
            args = null;
            return this;
        }

        public ArgumentDescriberFixture withOneArg() throws NoSuchMethodException {
            args = new Object[]{"some arg"};
            return this;
        }

        public ArgumentDescriberFixture withMultipleArgs() throws NoSuchMethodException {
            args = new Object[]{"one", "two"};
            return this;
        }

        public ArgumentDescriberFixture withAnException() throws NoSuchMethodException {
            args = new Object[]{new Exception()};
            return this;
        }

        public ArgumentDescriberFixture withArgAndException() {
            args = new Object[]{"an arg", new Exception()};
            return this;
        }
    }
}

