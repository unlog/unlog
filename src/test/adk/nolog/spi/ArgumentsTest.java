package adk.nolog.spi;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class ArgumentsTest {

    @Test
    public void shouldHandleNoArgs() {
        Arguments arguments = givenAnArgumentDescriber().withNoArgs().build();
        assertThat(arguments.args(), is(not(nullValue())));
        assertThat(arguments.args().size(), is(0));
        assertThat(arguments.throwableArg(), is(nullValue()));
    }

    @Test
    public void shouldHandleOneArg() throws NoSuchMethodException {
        Arguments arguments = givenAnArgumentDescriber().withOneArg().build();
        assertThat(arguments.args().size(), is(1));
        assertThat(arguments.throwableArg(), is(nullValue()));
    }

    @Test
    public void shouldHandleMultipleArgs() throws NoSuchMethodException {
        Arguments arguments = givenAnArgumentDescriber().withMultipleArgs().build();
        assertThat(arguments.args().size(), is(greaterThan(1)));
        assertThat(arguments.throwableArg(), is(nullValue()));
    }

    @Test
    public void shouldHandleNoArgsAndAnException() throws NoSuchMethodException {
        Arguments arguments = givenAnArgumentDescriber().withAnException().build();
        assertThat(arguments.args().size(), is(0));
        assertThat(arguments.throwableArg(), is(not(nullValue())));
    }

    @Test
    public void shouldHandleArgAndException() {
        Arguments arguments = givenAnArgumentDescriber().withArgAndException().build();
        assertThat(arguments.args().size(), is(1));
        assertThat(arguments.throwableArg(), is(not(nullValue())));
    }


    private ArgumentDescriberFixture givenAnArgumentDescriber() {
        return new ArgumentDescriberFixture();
    }


    private class ArgumentDescriberFixture {

        private Object[] args;

        private Arguments build() {
            return new Arguments(args);
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

