package adk.nolog;

import adk.nolog.jul.JavaUtilLoggingLevelMap;

public enum Level {
    DEBUG {
        @Override
        java.util.logging.Level mapLevel(JavaUtilLoggingLevelMap levelMap) {
            return levelMap.mapDebug();
        }
    },
    ERROR {
        @Override
        java.util.logging.Level mapLevel(JavaUtilLoggingLevelMap levelMap) {
            return levelMap.mapError();
        }
    };

    abstract java.util.logging.Level mapLevel(JavaUtilLoggingLevelMap levelMap);
}
