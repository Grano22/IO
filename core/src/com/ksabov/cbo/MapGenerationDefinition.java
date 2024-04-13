package com.ksabov.cbo;

import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class MapGenerationDefinition {
    private final int mWidth;
    private final int mHeight;
    private final int tileSize;

    public MapGenerationDefinition(int mapWidth, int mapHeight, int newTileSize) {
        mWidth = mapWidth;
        mHeight = mapHeight;
        tileSize = newTileSize;
    }

    public int mapWidth() {
        return mWidth;
    }

    public int mapHeight() {
        return mHeight;
    }

    public int tileSize() {
        return tileSize;
    }
}
