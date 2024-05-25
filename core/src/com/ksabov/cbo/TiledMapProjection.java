package com.ksabov.cbo;

import java.util.ArrayList;

public class TiledMapProjection {
    final static public int MARKER_NEUTRAL_FLOOR = 0;
    final static public int MARKER_WALL = 1;
    final static public int MARKER_ROOM = 2;

    private final ArrayList<int[][]> markerLayers;
    private final int sizeX;
    private final int sizeXY;
    private final int tileSize;

    public TiledMapProjection(int width, int height, int tileSize) {
        markerLayers = new ArrayList<int[][]>() {{ add(new int[width][height]); }};
        sizeX = width;
        sizeXY = height;
        this.tileSize = tileSize;
    }

    public int[][] getRawMatrixMarkersLayer(int layerId) {
        return markerLayers.get(layerId);
    }

    public int getWidth() {
        return sizeX;
    }

    public int getHeight() {
        return sizeXY;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMarkersLayersCount() {
        return markerLayers.size();
    }
}
