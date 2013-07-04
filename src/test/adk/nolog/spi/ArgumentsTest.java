package adk.nolog.spi;

import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class ArgumentsTest {

    @Test
    public void shouldHandleNoArgs() {
        Arguments arguments = givenArguments().withNoArgs().build();
        assertThat(arguments.args(), is(not(nullValue())));
        assertThat(arguments.size(), is(0));
        assertThat(arguments.throwableArg(), is(nullValue()));
    }

    @Test
    public void shouldHandleOneArg() throws NoSuchMethodException {
        Arguments arguments = givenArguments().withOneArg().build();
        assertThat(arguments.size(), is(1));
        assertThat(arguments.throwableArg(), is(nullValue()));
    }

    @Test
    public void shouldHandleMultipleArgs() throws NoSuchMethodException {
        Arguments arguments = givenArguments().withMultipleArgs().build();
        assertThat(arguments.size(), is(greaterThan(1)));
        assertThat(arguments.throwableArg(), is(nullValue()));
    }

    @Test
    public void shouldHandleNoArgsAndAnException() throws NoSuchMethodException {
        Arguments arguments = givenArguments().withAnException().build();
        assertThat(arguments.size(), is(0));
        assertThat(arguments.throwableArg(), is(not(nullValue())));
    }

    @Test
    public void shouldHandleArgAndException() {
        Arguments arguments = givenArguments().withArgAndException().build();
        assertThat(arguments.size(), is(1));
        assertThat(arguments.throwableArg(), is(not(nullValue())));
    }


    private ArgumentFixture givenArguments() {
        return new ArgumentFixture();
    }


    private class ArgumentFixture {

        private Object[] args;

        private Arguments build() {
            return new Arguments(args);
        }

        private ArgumentFixture withNoArgs() {
            args = null;
            return this;
        }

        public ArgumentFixture withOneArg() throws NoSuchMethodException {
            args = new Object[]{"some arg"};
            return this;
        }

        public ArgumentFixture withMultipleArgs() throws NoSuchMethodException {
            args = new Object[]{"one", "two"};
            return this;
        }

        public ArgumentFixture withAnException() throws NoSuchMethodException {
            args = new Object[]{new Exception()};
            return this;
        }

        public ArgumentFixture withArgAndException() {
            args = new Object[]{"an arg", new Exception()};
            return this;
        }
    }
}

