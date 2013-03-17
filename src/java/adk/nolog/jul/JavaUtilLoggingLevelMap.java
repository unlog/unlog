package adk.nolog.jul;

import adk.nolog.spi.LevelMap;

import java.util.logging.Level;

public class JavaUtilLoggingLevelMap implements LevelMap<Level> {
    public static final Level DEFAULT_LOG_LEVEL = Level.FINEST;

    @Override
    public java.util.logging.Level mapError() {
        return java.util.logging.Level.SEVERE;
    }

    @Override
    public Level mapDebug() {
        return Level.FINEST;
    }
}
