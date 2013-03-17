package adk.nolog.spi;

public interface LevelMap<L> {
    L mapError();

    L mapDebug();
}
