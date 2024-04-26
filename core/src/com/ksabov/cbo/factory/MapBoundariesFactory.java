package com.ksabov.cbo.factory;

import com.badlogic.gdx.maps.MapLayer;
import com.ksabov.cbo.MapActor;
import com.ksabov.cbo.objects.Wall;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MapBoundariesFactory {
    public static ArrayList<Wall> createMapBoundaries(int x, int y, int width, int height) {
        ArrayList<Wall> walls = new ArrayList<>();

        final int wallWidth = 20;
        Wall leftWall = new Wall(x - wallWidth, y, wallWidth, height);
        leftWall.setName("mapLeftWall");
        walls.add(leftWall);

        Wall rightWall = new Wall(width, y, wallWidth, height);
        rightWall.setName("mapRightWall");
        walls.add(rightWall);

        Wall topWall = new Wall(x - wallWidth, y, width + 2 * wallWidth, wallWidth);
        topWall.setName("mapTopWall");
        walls.add(topWall);

        Wall bottomWall = new Wall(x - wallWidth, height, width + 2 * wallWidth, wallWidth);
        bottomWall.setName("mapBottomWall");
        walls.add(bottomWall);

        return walls;
    }
}
