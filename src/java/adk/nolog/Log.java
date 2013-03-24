package adk.nolog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    LogLevel level() default LogLevel.DEBUG;
}
