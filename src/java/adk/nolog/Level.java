package adk.nolog;

import adk.nolog.spi.LevelMap;

public enum Level {
    DEBUG {
        @Override
        <L> L mapLevel(LevelMap<L> levelMap) {
            return levelMap.mapDebug();
        }
    },
    ERROR {
        @Override
        <L> L mapLevel(LevelMap<L> levelMap) {
            return levelMap.mapError();
        }
    }, INFO {
        @Override
        <L> L mapLevel(LevelMap<L> levelMap) {
            return levelMap.mapInfo();
        }
    }, WARNING {
        @Override
        <L> L mapLevel(LevelMap<L> levelMap) {
            return levelMap.mapWarning();
        }
    };

    abstract <L> L mapLevel(LevelMap<L> levelMap);
}
