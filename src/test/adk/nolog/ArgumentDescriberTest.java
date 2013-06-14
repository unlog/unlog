package adk.nolog;

import adk.nolog.spi.ArgumentDescriber;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class ArgumentDescriberTest {

    @Test
    @Ignore
    public void shouldHandleNoArgs() {
        ArgumentDescriber argumentDescriber = new ArgumentDescriber(method(), new Object[0]);
        assertThat(argumentDescriber.args(), is(not(nullValue())));
        assertThat(argumentDescriber.args().length, is(0));
    }

    private Method method() {
        throw new UnsupportedOperationException();
    }
}

interface TheMethods {
    void noArgs();
}
