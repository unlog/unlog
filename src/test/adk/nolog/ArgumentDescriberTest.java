package adk.nolog;

import adk.nolog.spi.ArgumentDescriber;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class ArgumentDescriberTest {

    @Test
    public void shouldHandleNoArgs() {

        ArgumentDescriber argumentDescriber = givenAnArgumentDescriber().withNoArgs().build();
        assertThat(argumentDescriber.args(), is(not(nullValue())));
        assertThat(argumentDescriber.args().length, is(0));
    }

    private ArgumentDescriberFixture givenAnArgumentDescriber() {
        return new ArgumentDescriberFixture();
    }

    @Test
    public void shouldHandleOneArg() {

    }


    private class ArgumentDescriberFixture {

        private Object[] args;
        private Method method;

        private ArgumentDescriber build() {
            return new ArgumentDescriber(method, args);
        }

        private ArgumentDescriberFixture withNoArgs() {
            args = null;
            return this;
        }
    }
}

