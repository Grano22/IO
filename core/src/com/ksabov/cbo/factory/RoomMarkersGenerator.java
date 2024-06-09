package com.ksabov.cbo.factory;

import com.badlogic.gdx.math.Vector2;
import com.ksabov.cbo.Room;
import com.ksabov.cbo.TiledMapProjection;
import com.ksabov.cbo.objects.Wall;

import java.util.ArrayList;
import java.util.Random;

public class RoomMarkersGenerator {
    final Random randomizer = new Random();

    public RoomMarkersGenerator() {

    }

    public ArrayList<Vector2> generate(TiledMapProjection mapProjection) {
        int[][] nextMarkers/* = mapProjection.getRawMatrixMarkersLayer(0)*/ = new int[mapProjection.getWidth()][mapProjection.getHeight()];
        ArrayList<Vector2> wallMarkers = new ArrayList<>();

        final int minRoomWidth = 10;
        final int maxRoomWidth = 25;
        final int minRoomHeight = 10;
        final int maxRoomHeight = 25;

        int nextRoomWidth = randomizer.nextInt(maxRoomWidth - minRoomWidth) + minRoomWidth;
        int nextRoomHeight = randomizer.nextInt(maxRoomHeight - minRoomHeight) + minRoomHeight;

        for (int posX = 0; posX < mapProjection.getWidth(); posX++) {
            for (int posY = 0; posY < mapProjection.getHeight(); posY++) {
                if (posX % nextRoomWidth == 0) {
                    nextMarkers[posX][posY] = TiledMapProjection.MARKER_WALL;
                }

                if (posY % nextRoomHeight == 0) {
                    nextMarkers[posX][posY] = TiledMapProjection.MARKER_WALL;
                }

                if (posX == 0 || posY == 0 || posX == mapProjection.getWidth() - 1 || posY == mapProjection.getHeight() - 1) {
                    nextMarkers[posX][posY] = TiledMapProjection.MARKER_WALL;
                }

                if ((posX % nextRoomWidth == 0 && posY % nextRoomHeight == 0) || (posY == mapProjection.getHeight() - 1 && posX == mapProjection.getWidth() - 1) || (posX == mapProjection.getWidth() - 1 && posY % nextRoomHeight == 0) || (posY == mapProjection.getHeight() - 1  && posX % nextRoomWidth == 0)) {
                    nextMarkers[posX][posY] = TiledMapProjection.MARKER_ROOM_WALL_CORNER;
                    wallMarkers.add(new Vector2(posX, posY));
                }
            }
        }

        mapProjection.setRawMatrixMarkersLayer(1, nextMarkers);

        return wallMarkers;
    }
}
