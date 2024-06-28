package com.ksabov.cbo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomGenerator {
    private static final int GRID_ROWS = 4; // Number of rows in the grid
    private static final int GRID_COLS = 5; // Number of columns in the grid
    private static final int ROOM_WIDTH = 150; // Width of each room
    private static final int ROOM_HEIGHT = 100; // Height of each room
    private static final int WALL_THICKNESS = 10; // Thickness of the walls between rooms
    private static final int MAP_WIDTH = GRID_COLS * (ROOM_WIDTH + WALL_THICKNESS); // Total width of the map
    private static final int MAP_HEIGHT = GRID_ROWS * (ROOM_HEIGHT + WALL_THICKNESS); // Total height of the map
    private static final int BORDER_SIZE = 50; // Size of the border around the map

    private List<Rectangle> rooms;
    private List<Rectangle> walls;

    public ChatRoomGenerator() {
        rooms = new ArrayList<>();
        walls = new ArrayList<>();
        generateRooms();
        generateWalls();
        generateBoundaryWalls(); // Add this line
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

    private void generateWalls() {
        // Vertical walls between rooms
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 1; col < GRID_COLS; col++) {
                int x = col * (ROOM_WIDTH + WALL_THICKNESS) - WALL_THICKNESS / 2 + BORDER_SIZE;
                int y = row * (ROOM_HEIGHT + WALL_THICKNESS) + BORDER_SIZE;
                int height = ROOM_HEIGHT;
                walls.add(new Rectangle(x, y, WALL_THICKNESS, height));
            }
        }

        // Horizontal walls between rooms
        for (int row = 1; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                int x = col * (ROOM_WIDTH + WALL_THICKNESS) + BORDER_SIZE;
                int y = row * (ROOM_HEIGHT + WALL_THICKNESS) - WALL_THICKNESS / 2 + BORDER_SIZE;
                int width = ROOM_WIDTH;
                walls.add(new Rectangle(x, y, width, WALL_THICKNESS));
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

    public List<Rectangle> getRooms() {
        return rooms;
    }

    public List<Rectangle> getWalls() {
        return walls;
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
}