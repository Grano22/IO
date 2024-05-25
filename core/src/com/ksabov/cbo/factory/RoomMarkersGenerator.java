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
        int[][] nextMarkers = mapProjection.getRawMatrixMarkersLayer(0);

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

//        int howMuchRemainingTilesX = mapProjection.getWidth();
//        int howMuchRemainingTilesY = mapProjection.getHeight();
//        int howMuchRoomsX = 0, howMuchRoomsY = 0;
//
//        int iter = 0, nextX = 0, nextY = 0;
//
//        while (howMuchRemainingTilesX > 0 || howMuchRemainingTilesY > 0) {
//            int nextSizeX = howMuchRemainingTilesX <= maxRoomHeight ? howMuchRemainingTilesX : randomizer.nextInt(maxRoomHeight - minRoomWidth) + minRoomWidth;
//            int nextSizeY = howMuchRemainingTilesY <= maxRoomHeight ? howMuchRemainingTilesY : randomizer.nextInt(maxRoomHeight - minRoomWidth) + minRoomWidth;
//
//            howMuchRemainingTilesX -= nextSizeX;
//            howMuchRemainingTilesY -= nextSizeY;
//
//            nextMarkers[nextX][nextY] = TiledMapProjection.MARKER_WALL;
//
//            //nextX += mapProjection.tileSize() * nextSizeX;
//            nextY += nextSizeY;
//
//            howMuchRoomsX += nextSizeX;
//            howMuchRoomsY += nextSizeY;
//
//            iter++;
//
//            if (howMuchRemainingTilesY <= 0 && nextX < mapProjection.getWidth()) {
//                break;
////                howMuchRemainingTilesY = definition.mapHeight();
////                nextY = 0;
////                nextX += definition.tileSize() * nextSizeX;
//            }
//
//            if (iter >= 1000) {
//                break;
//            }
//        }

        mapProjection.setRawMatrixMarkersLayer(0, nextMarkers);
    }
}
