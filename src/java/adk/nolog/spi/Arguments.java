package adk.nolog.spi;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Arguments {
    private final LinkedList<Object> args = new LinkedList<Object>();
    private Throwable throwable;

    public Arguments(Object[] args) {
        if (args != null) {
            this.args.addAll(Arrays.asList(args));
        }
        throwable = getThrowableFromArgsList();
        this.args.remove(throwable);
    }

    public List<Object> args() {
        return args;
    }

    public Throwable throwableArg() {
        return throwable;
    }

    private Throwable getThrowableFromArgsList() {
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

    int size() {
        return args().size();
    }

    public Object[] argsAsArray() {
        return args().toArray();
    }
}
