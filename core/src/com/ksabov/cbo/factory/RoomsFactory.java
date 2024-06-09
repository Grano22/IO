package com.ksabov.cbo.factory;

import com.badlogic.gdx.math.Vector2;
import com.ksabov.cbo.MapGenerationDefinition;
import com.ksabov.cbo.Room;
import com.ksabov.cbo.objects.Wall;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;


public class RoomsFactory {
    public ArrayList<Room> create(ArrayList<Vector2> cornerMarkers)
    {
        ArrayList<Room> rooms = new ArrayList<>();

        System.out.println(cornerMarkers);
        System.out.println(cornerMarkers.size());

        for (int i = 0; i < cornerMarkers.size() - 1; i++) {
//            System.out.println(cornerMarkers.get(i + 1).x - cornerMarkers.get(i).x);
//            System.out.println(cornerMarkers.get(i + 1).y - cornerMarkers.get(i).y);

            rooms.add(
                new Room(
                    "room" + i,
                        cornerMarkers.get(i),
                        cornerMarkers.get(i + 1).x - cornerMarkers.get(i).x,
                        cornerMarkers.get(i + 1).y - cornerMarkers.get(i).y
//                     new ArrayList<Wall>() {{
//                         add(new Wall(finalNextX, finalNextY + definition.tileSize(), definition.tileSize(), nextWallHeight));
//                         add(new Wall(finalNextX, finalNextY, nextWallWidth, definition.tileSize()));
//                         add(new Wall(finalNextX + nextWallHeight, finalNextY, definition.tileSize(), nextWallHeight));
//                         add(new Wall(finalNextX, finalNextY + nextWallHeight + definition.tileSize(), nextWallWidth, definition.tileSize()));
//                     }}
                )
            );
        }

        return rooms;
    }
}
