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

        int howFirstDimension = 0;

        while (cornerMarkers.get(howFirstDimension).x == 0.) {
            howFirstDimension++;
        }

        int howMuchShards = cornerMarkers.size() / howFirstDimension;

        for (int i = 0; i < howMuchShards - 1; i++) {
            for (int j = 0; j < howFirstDimension - 1; j++) {
                int iter = (i * howFirstDimension) + j;
                Vector2 nextDimCorner = cornerMarkers.get((i + 1) * howFirstDimension + j);
                float roomWidth = nextDimCorner.x - cornerMarkers.get(iter).x;
                float roomHeight = cornerMarkers.get(iter + 1).y - cornerMarkers.get(iter).y;

                rooms.add(
                        new Room(
                                "room" + i,
                                cornerMarkers.get(iter),
                                roomWidth,
                                roomHeight
//                     new ArrayList<Wall>() {{
//                         add(new Wall(finalNextX, finalNextY + definition.tileSize(), definition.tileSize(), nextWallHeight));
//                         add(new Wall(finalNextX, finalNextY, nextWallWidth, definition.tileSize()));
//                         add(new Wall(finalNextX + nextWallHeight, finalNextY, definition.tileSize(), nextWallHeight));
//                         add(new Wall(finalNextX, finalNextY + nextWallHeight + definition.tileSize(), nextWallWidth, definition.tileSize()));
//                     }}
                        )
                );
            }
        }

        return rooms;
    }
}
