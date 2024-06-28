package com.ksabov.cbo;

import com.badlogic.gdx.math.Rectangle;

import java.util.List;
import java.util.Random;

public class RoomOperator {
    public Rectangle randomizeRoom(List<Rectangle> rooms) {
        Random random = new Random();
        int randomIndex = random.nextInt(rooms.size());
        //System.out.println("Player moved to room: " + this.currentRoom.getName());

        return rooms.get(randomIndex);
    }
}
