package com.github.unlog.internal.format;

import com.github.unlog.spi.Arguments;

public class Joiner implements Arguments.Iterator {
    private final String separator;
    private String currentSeparator = "";
    private StringBuilder joined = new StringBuilder();

    public Joiner(String separator) {
        this.separator = separator;
    }

    @Override
    public Arguments.Iterator element(Object el) {
        joined.append(currentSeparator);
        joined.append(el);
        currentSeparator = separator;
        return this;
    }

    @Override
    public String toString() {
        return joined.toString();
    }
}
