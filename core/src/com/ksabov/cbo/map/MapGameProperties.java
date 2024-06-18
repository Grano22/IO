package com.ksabov.cbo.map;

public enum MapGameProperties {
    BLOCKING("BLOCKING"),
    POSITION_X("POSITION_X"),
    POSITION_Y("POSITION_Y");

    private final String text;

    MapGameProperties(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
