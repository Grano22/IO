package com.ksabov.cbo.factory;

import com.badlogic.gdx.math.Vector2;
import com.ksabov.cbo.MapGenerationDefinition;
import com.ksabov.cbo.Room;
import com.ksabov.cbo.objects.Wall;

import java.util.ArrayList;
import java.util.Random;

public class RoomsFactory {
    Random randomizer = new Random();

    public ArrayList<Room> create(MapGenerationDefinition definition)
    {
        ArrayList<Room> rooms = new ArrayList<>();

        int howMuchTilesX = definition.mapWidth() / definition.tileSize();
        int howMuchTilesY = definition.mapHeight() / definition.tileSize();

        System.out.println(howMuchTilesX);
        System.out.println(howMuchTilesY);

        int iter = 0, nextX = 0, nextY = 0;
        while (howMuchTilesX > 0 || howMuchTilesY > 0) {
            int nextSizeX = randomizer.nextInt(5 - 3) + 3;
            int nextSizeY = randomizer.nextInt(5 - 3) + 3;

            howMuchTilesY -= nextSizeY;
            howMuchTilesX -= nextSizeX;

            rooms.add(
                new Room(
                    "room" + iter,
                     new Vector2(nextX, nextY + nextSizeY * definition.tileSize()),
                    nextSizeX * definition.tileSize(),
                    nextSizeY * definition.tileSize(),
                     new ArrayList<Wall>() {{
                         add(new Wall(nextX, nextY + nextSizeY * definition.tileSize(), 20, nextY + nextSizeY * definition.tileSize()));
                     }}
                )
            );

            iter++;
        }

        return rooms;
    }
}
