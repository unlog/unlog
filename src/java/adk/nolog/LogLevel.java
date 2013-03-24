package adk.nolog;

import adk.nolog.spi.LevelMap;

public enum LogLevel {
    DEBUG {
        @Override
        public <L> L mapLevel(LevelMap<L> levelMap) {
            return levelMap.mapDebug();
        }
    },
    ERROR {
        @Override
        public <L> L mapLevel(LevelMap<L> levelMap) {
            return levelMap.mapError();
        }
    }, INFO {
        @Override
        public <L> L mapLevel(LevelMap<L> levelMap) {
            return levelMap.mapInfo();
        }
    }, WARNING {
        @Override
        public <L> L mapLevel(LevelMap<L> levelMap) {
            return levelMap.mapWarning();
        }
    };

    public abstract <L> L mapLevel(LevelMap<L> levelMap);
}
