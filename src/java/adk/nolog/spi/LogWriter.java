package adk.nolog.spi;

public interface LogWriter {
    void writeLogEvent(LogEvent logEvent);
}
