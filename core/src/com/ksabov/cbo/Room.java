package com.ksabov.cbo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Room {
    private Texture wallTexture;
    private Texture doorTexture;
    private List<Rectangle> walls;
    private List<Rectangle> doors;
    private List<Item> items;

    public Room(Texture wallTexture, Texture doorTexture) {
        this.wallTexture = wallTexture;
        this.doorTexture = doorTexture;
        walls = new ArrayList<>();
        doors = new ArrayList<>();
        items = new ArrayList<>();
    }

    public void addWall(Rectangle wall) {
        walls.add(wall);
    }

    public void addDoor(Rectangle door) {
        doors.add(door);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Rectangle> getWalls() {
        return walls;
    }

    public List<Rectangle> getDoors() {
        return doors;
    }

    public List<Item> getItems() {
        return items;
    }

    public void render(SpriteBatch batch) {
        // Render walls
        for (Rectangle wall : walls) {
            batch.draw(wallTexture, wall.x, wall.y, wall.width, wall.height);
        }

        // Render doors
        for (Rectangle door : doors) {
            batch.draw(doorTexture, door.x, door.y, door.width, door.height);
        }
    }

    public boolean tryOpenDoor(Rectangle playerBounds) {
        Iterator<Rectangle> iterator = doors.iterator();
        while (iterator.hasNext()) {
            Rectangle door = iterator.next();
            if (door.overlaps(playerBounds)) {
                iterator.remove(); // Remove the door from the list
                return true;
            }
        }
        return false;
    }
}