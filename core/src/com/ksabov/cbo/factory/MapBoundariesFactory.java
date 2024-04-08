package com.ksabov.cbo.factory;

import com.badlogic.gdx.maps.MapLayer;
import com.ksabov.cbo.MapActor;
import com.ksabov.cbo.Wall;

public class MapBoundariesFactory {
    public static MapLayer createMapBoundaries(int x, int y, int width, int height) {
        MapLayer mapLayer = new MapLayer();

        final int wallWidth = 20;
        Wall leftWall = new Wall(x - wallWidth, y, wallWidth, height);
        mapLayer.getObjects().add(new MapActor(leftWall));

        Wall rightWall = new Wall(width, y, wallWidth, height);
        mapLayer.getObjects().add(new MapActor(rightWall));

        Wall topWall = new Wall(x - wallWidth, y, width + 2 * wallWidth, wallWidth);
        mapLayer.getObjects().add(new MapActor(topWall));

        Wall bottomWall = new Wall(x - wallWidth, height, width + 2 * wallWidth, wallWidth);
        mapLayer.getObjects().add(new MapActor(bottomWall));

        return mapLayer;
    }
}
