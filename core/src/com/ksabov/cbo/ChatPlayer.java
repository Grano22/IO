package com.ksabov.cbo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.List;

public class ChatPlayer {
    private Texture texture;
    private Rectangle bounds;
    private Vector2 position;
    private float speed;

    public ChatPlayer(Texture texture, float x, float y, float width, float height, float speed) {
        this.texture = texture;
        this.bounds = new Rectangle(x, y, width, height);
        this.position = new Vector2(x, y);
        this.speed = speed;
    }

    public void update(float delta, List<Rectangle> walls, List<Rectangle> doors) {
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

        // Handle collision with walls
        for (Rectangle wall : walls) {
            if (bounds.overlaps(wall)) {
                position.set(oldPosition); // Revert to old position
                bounds.setPosition(position);
                break; // Exit collision detection loop
            }
        }

        // Handle interaction with doors (spacebar)
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            for (Rectangle door : doors) {
                if (bounds.overlaps(door)) {
                    // Move the player to the corresponding room
                    moveThroughDoor(door, walls, doors);
                    break; // Exit door interaction loop
                }
            }
        }
    }

    private void moveThroughDoor(Rectangle door, List<Rectangle> walls, List<Rectangle> doors) {
        // Adjust the player's position based on the door they passed through
        for (Rectangle wall : walls) {
            if (door.overlaps(wall)) {
                doors.remove(door); // Remove the door from the list to make it one-time passable

                // Check the direction of the door and move the player accordingly
                if (door.width > door.height) { // Horizontal door
                    if (position.y > door.y) { // Moving up
                        position.y += bounds.height;
                    } else { // Moving down
                        position.y -= bounds.height;
                    }
                } else { // Vertical door
                    if (position.x > door.x) { // Moving right
                        position.x += bounds.width;
                    } else { // Moving left
                        position.x -= bounds.width;
                    }
                }

                bounds.setPosition(position);
                break; // Exit the loop after finding the relevant door
            }
        }
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

    public void setPosition(float x, float y) {
        position.set(x, y);
        bounds.setPosition(position);
    }
}