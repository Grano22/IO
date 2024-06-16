package com.ksabov.cbo.map;

public enum MapGameProperties {
    BLOCKING("BLOCKING");

    private final String text;

    MapGameProperties(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
