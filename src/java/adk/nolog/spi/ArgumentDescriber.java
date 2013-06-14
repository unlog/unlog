package adk.nolog.spi;

import java.lang.reflect.Method;

public class ArgumentDescriber {
    private final Method method;
    private final Object[] args;

    public ArgumentDescriber(Method method, Object[] args) {
        this.method = method;
        this.args = args == null ? new Object[0] : args;
    }

    public Object[] args() {
        return args;
    }
}
