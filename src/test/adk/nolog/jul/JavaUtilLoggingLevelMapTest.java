package adk.nolog.jul;

import org.junit.Test;

import java.util.logging.Level;

import static adk.nolog.LogLevel.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class JavaUtilLoggingLevelMapTest {

    @Test
    public void shouldMapAllLogLevels() {
        JavaUtilLoggingLevelMap levelMap = new JavaUtilLoggingLevelMap();
        assertThat(DEBUG.mapLevel(levelMap), is(Level.FINEST));
        assertThat(INFO.mapLevel(levelMap), is(Level.INFO));
        assertThat(WARNING.mapLevel(levelMap), is(Level.WARNING));
        assertThat(ERROR.mapLevel(levelMap), is(Level.SEVERE));
    }
}
