package adk.nolog.jul;

import adk.nolog.spi.LevelMap;

import java.util.logging.Level;

public class JavaUtilLoggingLevelMap implements LevelMap<Level> {

    @Override
    public java.util.logging.Level mapError() {
        return java.util.logging.Level.SEVERE;
    }

    @Override
    public Level mapDebug() {
        return Level.FINEST;
    }

    @Override
    public Level mapInfo() {
        return Level.INFO;
    }

    @Override
    public Level mapWarning() {
        return Level.WARNING;
    }
}
