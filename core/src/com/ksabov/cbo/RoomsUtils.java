package com.ksabov.cbo;

import com.badlogic.gdx.math.Rectangle;

import java.util.List;

public class RoomsUtils {
    private final List<Rectangle> rooms;

    public RoomsUtils(List<Rectangle> rooms) {
        this.rooms = rooms;
    }

    public List<Rectangle> getRooms() {
        return rooms;
    }

    public Rectangle getFarthestRoomToThePlayer(Player player) {
        Rectangle farthestRoom = null;
        double maxDistance = -1;
        for (Rectangle room : rooms) {
            double distance = calculateDistance(player.getCurrentRoom(), room);
            if (distance > maxDistance) {
                maxDistance = distance;
                farthestRoom = room;
            }
        }

        return farthestRoom;
    }

    private double calculateDistance(Rectangle room1, Rectangle room2) {
        return Math.sqrt(Math.pow(room2.x - room1.x, 2) + Math.pow(room2.y - room1.y, 2));
    }
}
