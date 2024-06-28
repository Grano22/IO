package com.ksabov.cbo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomGenerator {
    private static final int GRID_ROWS = 4; // Number of rows in the grid
    private static final int GRID_COLS = 5; // Number of columns in the grid
    private static final int ROOM_WIDTH = 300; // Width of each room (increased)
    private static final int ROOM_HEIGHT = 200; // Height of each room (increased)
    private static final int WALL_THICKNESS = 10; // Thickness of the walls between rooms
    private static final int DOOR_WIDTH = 60; // Width of doors (wider than player)
    private static final int DOOR_HEIGHT = 60; // Height of doors
    private static final int MAP_WIDTH = GRID_COLS * (ROOM_WIDTH + WALL_THICKNESS); // Total width of the map
    private static final int MAP_HEIGHT = GRID_ROWS * (ROOM_HEIGHT + WALL_THICKNESS); // Total height of the map
    private static final int BORDER_SIZE = 50; // Size of the border around the map
    private static final int ITEM_SIZE = 20; // Size of items
    private static final int NUM_ITEMS = 10; // Number of items to generate

    private List<Rectangle> rooms;
    private List<Rectangle> walls;
    private List<Rectangle> doors;
    private List<Rectangle> items; // Added items list

    public ChatRoomGenerator() {
        rooms = new ArrayList<>();
        walls = new ArrayList<>();
        doors = new ArrayList<>();
        items = new ArrayList<>(); // Initialize items list
        generateRooms();
        generateWallsAndDoors();
        generateBoundaryWalls();
        generateItems();
    }

    private void generateRooms() {
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                int x = col * (ROOM_WIDTH + WALL_THICKNESS) + BORDER_SIZE;
                int y = row * (ROOM_HEIGHT + WALL_THICKNESS) + BORDER_SIZE;
                Rectangle room = new Rectangle(x, y, ROOM_WIDTH, ROOM_HEIGHT);
                rooms.add(room);
            }
        }
    }

    private void generateWallsAndDoors() {
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                int x = col * (ROOM_WIDTH + WALL_THICKNESS) + BORDER_SIZE;
                int y = row * (ROOM_HEIGHT + WALL_THICKNESS) + BORDER_SIZE;

                // Add vertical walls and doors
                if (col < GRID_COLS - 1) {
                    int wallX = x + ROOM_WIDTH;
                    int doorY = y + ROOM_HEIGHT / 2 - DOOR_HEIGHT / 2;

                    // Top part of the wall
                    walls.add(new Rectangle(wallX, y, WALL_THICKNESS, ROOM_HEIGHT / 2 - DOOR_HEIGHT / 2));
                    // Bottom part of the wall
                    walls.add(new Rectangle(wallX, doorY + DOOR_HEIGHT, WALL_THICKNESS, ROOM_HEIGHT / 2 - DOOR_HEIGHT / 2));
                    // Door
                    doors.add(new Rectangle(wallX, doorY, WALL_THICKNESS, DOOR_HEIGHT));
                }

                // Add horizontal walls and doors
                if (row < GRID_ROWS - 1) {
                    int wallY = y + ROOM_HEIGHT;
                    int doorX = x + ROOM_WIDTH / 2 - DOOR_WIDTH / 2;

                    // Left part of the wall
                    walls.add(new Rectangle(x, wallY, ROOM_WIDTH / 2 - DOOR_WIDTH / 2, WALL_THICKNESS));
                    // Right part of the wall
                    walls.add(new Rectangle(doorX + DOOR_WIDTH, wallY, ROOM_WIDTH / 2 - DOOR_WIDTH / 2, WALL_THICKNESS));
                    // Door
                    doors.add(new Rectangle(doorX, wallY, DOOR_WIDTH, WALL_THICKNESS));
                }
            }
        }
    }

    private void generateBoundaryWalls() {
        // Top boundary wall
        walls.add(new Rectangle(BORDER_SIZE - WALL_THICKNESS, BORDER_SIZE - WALL_THICKNESS, MAP_WIDTH + WALL_THICKNESS * 2, WALL_THICKNESS));
        // Bottom boundary wall
        walls.add(new Rectangle(BORDER_SIZE - WALL_THICKNESS, MAP_HEIGHT + BORDER_SIZE, MAP_WIDTH + WALL_THICKNESS * 2, WALL_THICKNESS));
        // Left boundary wall
        walls.add(new Rectangle(BORDER_SIZE - WALL_THICKNESS, BORDER_SIZE - WALL_THICKNESS, WALL_THICKNESS, MAP_HEIGHT + WALL_THICKNESS * 2));
        // Right boundary wall
        walls.add(new Rectangle(MAP_WIDTH + BORDER_SIZE, BORDER_SIZE - WALL_THICKNESS, WALL_THICKNESS, MAP_HEIGHT + WALL_THICKNESS * 2));
    }

    private void generateItems() {
        for (int i = 0; i < NUM_ITEMS; i++) {
            Rectangle item;
            boolean collides;
            do {
                collides = false;
                int x = MathUtils.random(BORDER_SIZE, MAP_WIDTH + BORDER_SIZE - ITEM_SIZE);
                int y = MathUtils.random(BORDER_SIZE, MAP_HEIGHT + BORDER_SIZE - ITEM_SIZE);
                item = new Rectangle(x, y, ITEM_SIZE, ITEM_SIZE);

                // Check collision with walls
                for (Rectangle wall : walls) {
                    if (item.overlaps(wall)) {
                        collides = true;
                        break;
                    }
                }
                // Check collision with doors
                for (Rectangle door : doors) {
                    if (item.overlaps(door)) {
                        collides = true;
                        break;
                    }
                }
            } while (collides);
            items.add(item);
        }
    }

    public List<Rectangle> getRooms() {
        return rooms;
    }

    public List<Rectangle> getWalls() {
        return walls;
    }

    public List<Rectangle> getDoors() {
        return doors;
    }

    public List<Rectangle> getItems() {
        return items;
    }

    public void renderRooms(SpriteBatch batch, Texture roomTexture) {
        for (Rectangle room : rooms) {
            batch.draw(roomTexture, room.x, room.y, room.width, room.height);
        }
    }

    public void renderWalls(SpriteBatch batch, Texture wallTexture) {
        for (Rectangle wall : walls) {
            batch.draw(wallTexture, wall.x, wall.y, wall.width, wall.height);
        }
    }

    public void renderDoors(SpriteBatch batch, Texture doorTexture) {
        for (Rectangle door : doors) {
            batch.draw(doorTexture, door.x, door.y, door.width, door.height);
        }
    }

    public void removeDoor(Rectangle door) {
        doors.remove(door);
    }

    public void renderItems(SpriteBatch batch, Texture itemTexture) {
        for (Rectangle item : items) {
            batch.draw(itemTexture, item.x, item.y, item.width, item.height);
        }
    }

    public void removeItem(Rectangle item) {
        items.remove(item);
    }
}
