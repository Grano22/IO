package com.ksabov.cbo.map;

import java.util.ArrayList;
import java.util.Arrays;

public class TiledMapProjection implements Cloneable {
    final static public int MARKER_NONE = 0;
    final static public int MARKER_NEUTRAL_FLOOR = 1;
    final static public int MARKER_WALL = 2;
    final static public int MARKER_ROOM = 3;
    final static public int MARKER_ROOM_WALL_CORNER = 4;
    final static public int MARKER_OPEN_DOOR = 5;
    final static public int MARKER_CLOSED_DOOR = 6;

    private final ArrayList<int[][]> markerLayers;
    private final int sizeX;
    private final int sizeXY;
    private final int tileSize;

    public TiledMapProjection(int width, int height, int tileSize) {
        markerLayers = new ArrayList<int[][]>() {{ add(new int[width][height]); }};
        Arrays.stream(markerLayers.get(0)).forEach(a -> Arrays.fill(a, 1));
        sizeX = width;
        sizeXY = height;
        this.tileSize = tileSize;
    }
//
//    public TiledMapProjection(TiledMapProjection that) {
//        this();
//    }

    public int[][] getRawMatrixMarkersLayer(int layerId) {
        return markerLayers.get(layerId);
    }

    public TiledMapProjection setRawMatrixMarkersLayer(int layerId, int[][] markers) {
        if (markerLayers.size() > layerId) {
            markerLayers.set(layerId, markers);

            return this;
        }

        markerLayers.add(layerId, markers);

        return this;
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

    @Override
    public TiledMapProjection clone() {
        try {
            return (TiledMapProjection)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
