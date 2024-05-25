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

        final int minRoomWidth = 10;
        final int maxRoomHeight = 25;

        int howMuchRemainingTilesX = definition.mapWidth();
        int howMuchRemainingTilesY = definition.mapHeight();
        int howMuchRoomsX = 0, howMuchRoomsY = 0;

        int iter = 0, nextX = 0, nextY = 0;

        while (howMuchRemainingTilesX > 0 || howMuchRemainingTilesY > 0) {
            int nextSizeX = howMuchRemainingTilesX <= maxRoomHeight ? howMuchRemainingTilesX : randomizer.nextInt(maxRoomHeight - minRoomWidth) + minRoomWidth;
            int nextSizeY = howMuchRemainingTilesY <= maxRoomHeight ? howMuchRemainingTilesY : randomizer.nextInt(maxRoomHeight - minRoomWidth) + minRoomWidth;
            int nextWallWidth = nextSizeY * definition.tileSize();
            int nextWallHeight = nextSizeY * definition.tileSize();

            howMuchRemainingTilesX -= nextSizeX;
            howMuchRemainingTilesY -= nextSizeY;

            int finalNextX = nextX;
            int finalNextY = nextY;

            rooms.add(
                new Room(
                    "room" + iter,
                     new Vector2(nextX, nextY + nextSizeY * definition.tileSize()),
                    nextSizeX * definition.tileSize(),
                    nextSizeY * definition.tileSize(),
                     new ArrayList<Wall>() {{
                         add(new Wall(finalNextX, finalNextY + definition.tileSize(), definition.tileSize(), nextWallHeight));
                         add(new Wall(finalNextX, finalNextY, nextWallWidth, definition.tileSize()));
                         add(new Wall(finalNextX + nextWallHeight, finalNextY, definition.tileSize(), nextWallHeight));
                         add(new Wall(finalNextX, finalNextY + nextWallHeight + definition.tileSize(), nextWallWidth, definition.tileSize()));
                     }}
                )
            );

            //nextX += definition.tileSize() * nextSizeX;
            nextY += definition.tileSize() * nextSizeY;

            howMuchRoomsX += nextSizeX;
            howMuchRoomsY += nextSizeY;

            iter++;

            if (howMuchRemainingTilesY <= 0 && nextX < definition.mapWidth() * definition.tileSize()) {
                break;
//                howMuchRemainingTilesY = definition.mapHeight();
//                nextY = 0;
//                nextX += definition.tileSize() * nextSizeX;
            }

            if (iter >= 1000) {
                break;
            }
        }

        System.out.println("Rendered rooms x - " + howMuchRoomsX);
        System.out.println("Rendered rooms y - " + howMuchRoomsY);

        return rooms;
    }
}
