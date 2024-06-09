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

    public void generate(TiledMapProjection mapProjection) {
        int[][] nextMarkers/* = mapProjection.getRawMatrixMarkersLayer(0)*/ = new int[mapProjection.getWidth()][mapProjection.getHeight()];

        final int minRoomWidth = 10;
        final int maxRoomHeight = 25;

//        final int minRoomWidth = 10;
//        final int maxRoomWidth = 25;
//        final int minRoomHeight = 10;
//        final int maxRoomHeight = 25;

        int nextRoomWidth = 0; int nextRoomHeight = 0;
        int remainingX = 0; int remainingY = 0;

        for (int posX = 0; posX < mapProjection.getWidth(); posX++) {
            for (int posY = 0; posY < mapProjection.getHeight(); posY++) {
                if (posX == 0 || posY == 0 || posX == mapProjection.getWidth() - 1 || posY == mapProjection.getHeight() - 1) {
                    nextMarkers[posX][posY] = TiledMapProjection.MARKER_WALL;
                    continue;
                }

                if (remainingX == 0) {
                    nextRoomWidth = posX >= mapProjection.getWidth() - maxRoomHeight ? mapProjection.getWidth() - maxRoomHeight - posX : randomizer.nextInt(maxRoomHeight - minRoomWidth) + minRoomWidth;
                }

                if (remainingY == 0) {
                    nextRoomHeight = posY >= mapProjection.getHeight() - maxRoomHeight ? mapProjection.getHeight() - maxRoomHeight - posY : randomizer.nextInt(maxRoomHeight - minRoomWidth) + minRoomWidth;
                }

                if (nextRoomWidth == 0 || nextRoomHeight == 0) {
                    continue;
                }

                if (posX % nextRoomWidth == 0) {
                    nextMarkers[posX][posY] = TiledMapProjection.MARKER_WALL;
                    remainingX--;
                }

                if (posY % nextRoomHeight == 0) {
                    nextMarkers[posX][posY] = TiledMapProjection.MARKER_WALL;
                    remainingY--;
                }
            }
        }

        mapProjection.setRawMatrixMarkersLayer(1, nextMarkers);
    }
}
