package com.ksabov.cbo.factory;

import com.badlogic.gdx.math.Vector2;
import com.ksabov.cbo.Room;
import com.ksabov.cbo.map.TiledMapProjection;

import java.util.ArrayList;
import java.util.Random;

public class ActionableMapFinisher {
    public TiledMapProjection finish(TiledMapProjection baseProjection, ArrayList<Room> rooms) {
        //int[][] wallLayers = baseProjection.getRawMatrixMarkersLayer(1);
        // Generate doors
        Random random = new Random();
        int[][] itemMarkers = new int[baseProjection.getWidth()][baseProjection.getHeight()];
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            Vector2 roomPosition = room.getPosition();
            int xIndex = (int) (roomPosition.x / baseProjection.getTileSize());
            int yIndex = (int) (roomPosition.y / baseProjection.getTileSize());

//            if (roomPosition.x != 0) {
//                int leftDoorPos = (int) (random.nextInt((int) (room.getWidth() + roomPosition.x)) + roomPosition.x);
//                nextMarkers[leftDoorPos / baseProjection.getTileSize()][yIndex] = TiledMapProjection.MARKER_OPEN_DOOR;
//                nextMarkers[leftDoorPos / baseProjection.getTileSize()][yIndex + 1] = TiledMapProjection.MARKER_OPEN_DOOR;
//                wallLayers[leftDoorPos / baseProjection.getTileSize()][yIndex] = 0;
//                wallLayers[leftDoorPos / baseProjection.getTileSize()][yIndex + 1] = 0;
//            }
//
//            if (roomPosition.y != 0) {
//                int bottomDoorPos = (int) (random.nextInt((int) (room.getHeight() + roomPosition.y)) + roomPosition.y);
//                nextMarkers[xIndex][bottomDoorPos / baseProjection.getTileSize()] = TiledMapProjection.MARKER_OPEN_DOOR;
//                nextMarkers[xIndex + 1][bottomDoorPos / baseProjection.getTileSize()] = TiledMapProjection.MARKER_OPEN_DOOR;
//                wallLayers[xIndex][bottomDoorPos / baseProjection.getTileSize()] = 0;
//                wallLayers[xIndex + 1][bottomDoorPos / baseProjection.getTileSize()] = 0;
//            }
//
//            if (roomPosition.x + room.getWidth() < baseProjection.getWidth()) {
//                int rightDoorPos = (int) (random.nextInt((int) (room.getWidth() + roomPosition.x)) + roomPosition.x);
//            }
//
//            if (roomPosition.y + room.getHeight() < baseProjection.getHeight()) {
//                int topDoorPos = (int) (random.nextInt((int) (room.getHeight() + roomPosition.y)) + roomPosition.y);
//            }

            // Generate intel

            int nextIntelPosX = (int) (random.nextInt((int) (room.getWidth() + roomPosition.x)) + roomPosition.x);
            int nextIntelPosY = (int) (random.nextInt((int) (room.getHeight() + roomPosition.y)) + roomPosition.y);
            int nextIndexX = nextIntelPosX / baseProjection.getTileSize();
            int nextIndexY = nextIntelPosX / baseProjection.getTileSize();


        }


        baseProjection.setRawMatrixMarkersLayer(2, itemMarkers);

        return baseProjection;
    }

    //private
}
