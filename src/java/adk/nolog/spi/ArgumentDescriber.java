package adk.nolog.spi;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ArgumentDescriber {
    private final LinkedList<Object> args = new LinkedList<Object>();
    private Throwable throwable;

    public ArgumentDescriber(Object[] args) {
        if (args != null) {
            this.args.addAll(Arrays.asList(args));
        }
        throwable = getThrowable();
        this.args.remove(throwable);
    }

    public List<Object> args() {
        return args;
    }

    public Throwable throwable() {
        return throwable;
    }

    private Throwable getThrowable() {
        return (Throwable) (hasThrowable() ? lastArg() : null);
    }

    private Object lastArg() {
        return args.getLast();
    }

    private boolean hasThrowable() {
        return hasArgs() && lastArg() instanceof Throwable;
    }

    private boolean hasArgs() {
        return !args.isEmpty();
    }
}
