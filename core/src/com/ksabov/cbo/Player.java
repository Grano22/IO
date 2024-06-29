package com.ksabov.cbo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.List;

public class Player {
    public static float PLAYER_WIDTH = 30;
    public static float PLAYER_HEIGHT = 30;

    private Texture texture;
    private Rectangle bounds;
    private Vector2 position;
    private float speed;
    private float interactionDistance = 40; // Distance from the door to interact
    private int points; // Points for collecting items
    private Rectangle currentRoom;
    private int life = 1;

    public Player(Texture texture, float x, float y, float width, float height, float speed) {
        this.texture = texture;
        this.bounds = new Rectangle(x, y, width, height);
        this.position = new Vector2(x, y);
        this.speed = speed;
        this.points = 0;
    }

    public void update(float delta, List<Rectangle> walls, List<Rectangle> doors, List<Rectangle> items, RoomGenerator roomGenerator) {
        Vector2 oldPosition = new Vector2(position);

        // Move player based on input
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            position.y += speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            position.y -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            position.x -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            position.x += speed * delta;
        }

        bounds.setPosition(position);

        // Handle collision with walls and closed doors
        boolean collidedWithWallOrDoor = false;
        for (Rectangle wall : walls) {
            if (bounds.overlaps(wall)) {
                collidedWithWallOrDoor = true;
                break;
            }
        }
        for (Rectangle door : doors) {
            if (bounds.overlaps(door)) {
                collidedWithWallOrDoor = true;
                break;
            }
        }

        // If collided with a wall or closed door, revert to old position
        if (collidedWithWallOrDoor) {
            position.set(oldPosition);
            bounds.setPosition(position);
        }

        // Handle interaction with doors (spacebar)
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            for (Rectangle door : doors) {
                if (isNearDoor(door)) {
                    // Remove the door and move the player to the corresponding room
                    roomGenerator.removeDoor(door);
                    moveThroughDoor(door);
                    break; // Exit door interaction loop
                }
            }
        }

        // Handle item collection
        for (Rectangle item : items) {
            if (bounds.overlaps(item)) {
                roomGenerator.removeItem(item);
                points++;
                break;
            }
        }
    }

    private boolean isNearDoor(Rectangle door) {
        float distance = interactionDistance + Math.max(bounds.width, bounds.height);
        if (door.width > door.height) { // Horizontal door
            return position.x > door.x - distance && position.x < door.x + door.width + distance && Math.abs(position.y - door.y) < interactionDistance;
        } else { // Vertical door
            return position.y > door.y - distance && position.y < door.y + door.height + distance && Math.abs(position.x - door.x) < interactionDistance;
        }
    }

    private void moveThroughDoor(Rectangle door) {
        // Adjust the player's position based on the door they passed through
        if (door.width > door.height) { // Horizontal door
            if (position.y > door.y) { // Moving up
                position.y += bounds.height + door.height;
            } else { // Moving down
                position.y -= bounds.height + door.height;
            }
        } else { // Vertical door
            if (position.x > door.x) { // Moving right
                position.x += bounds.width + door.width;
            } else { // Moving left
                position.x -= bounds.width + door.width;
            }
        }

        bounds.setPosition(position);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, bounds.width, bounds.height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPoints(int newPoints) {
        points = newPoints;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
        bounds.setPosition(position);
    }

    public void moveToRoom(Rectangle room) {
        currentRoom = room;
        setPosition(currentRoom.x, currentRoom.y);
    }

    public int getPoints() {
        return points;
    }

    public Rectangle getCurrentRoom() {
        return currentRoom;
    }

    public boolean isInRoom() {
        return currentRoom != null;
    }

    public boolean isInThisSameRoom(Rectangle room) {
        return currentRoom == room;
    }

    public boolean isAlive() {
        return life > 0;
    }

    public void takeDamage() {
        this.life--;
        System.out.println("life out!");
    }
}
