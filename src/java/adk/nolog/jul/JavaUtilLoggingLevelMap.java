package adk.nolog.jul;

import java.util.logging.Level;

public class JavaUtilLoggingLevelMap {
    public java.util.logging.Level mapError() {
        return java.util.logging.Level.SEVERE;
    }

    public Level mapDebug() {
        return Level.FINEST;
    }
}
