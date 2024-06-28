package com.ksabov.cbo;

import com.badlogic.gdx.math.Rectangle;

import java.util.Optional;

public class ItemsGenerator {
    private static final int ITEM_SIZE = 20; // Size of items

    public Optional<Rectangle> generateExitPoint(Player player, RoomsUtils rooms) {
        if (!rooms.getRooms().isEmpty()) {
            //Rectangle farthestRoom = rooms.get(rooms.size() - 1);
            Rectangle farthestRoom = rooms.getFarthestRoomToThePlayer(player);
            int exitX = (int) (farthestRoom.x + farthestRoom.width / 2 - ITEM_SIZE / 2);
            int exitY = (int) (farthestRoom.y + farthestRoom.height / 2 - ITEM_SIZE / 2);
            return Optional.of(new Rectangle(exitX, exitY, ITEM_SIZE, ITEM_SIZE));
        }

        return Optional.empty();
    }
}
