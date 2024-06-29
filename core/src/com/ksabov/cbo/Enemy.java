package com.ksabov.cbo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import java.util.function.Function;

public class Enemy extends Actor implements RendererAware {
    public static float DEFAULT_WIDTH = 30;
    public static float DEFAULT_HEIGHT = 30;

    private float x;
    private float y;
    private float width = DEFAULT_WIDTH;
    private float height = DEFAULT_HEIGHT;
    private final float initialX;
    private final float initialY;
    private final TextureRegion textureRegion;
    private Rectangle bounds;
    private Rectangle currentRoom;

    public Enemy(float x, float y, TextureRegion texture) {
        this.x = x;
        this.y = y;
        this.initialX = x;
        this.initialY = y;
        updateBounds();
        textureRegion = texture;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void moveTo(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public void returnToInitialPosition() {
        if (!isAtInitialPosition()) {
            if (x < initialX) x++;
            else if (x > initialX) x--;

            if (y < initialY) y++;
            else if (y > initialY) y--;

            updateBounds();
        }
    }

    public boolean isAtInitialPosition() {
        return this.x == initialX && this.y == initialY;
    }

    public void followPlayer(Player player, Function<MoveToAction, MoveToAction> collisionPositionApplier) {
        if (player.isInThisSameRoom(currentRoom)) {
            MoveToAction nextMove = new MoveToAction();
            nextMove.setStartPosition(this.x, this.y);
            nextMove.setActor(this);

            float nextX = nextMove.getStartX();
            float nextY = nextMove.getStartY();
            if (player.getPosition().x > nextX) nextX++;
            if (player.getPosition().x < nextX) nextX--;
            if (player.getPosition().y > nextY) nextY++;
            if (player.getPosition().y < nextY) nextY--;

            nextMove.setPosition(nextX, nextY);
            nextMove = collisionPositionApplier.apply(nextMove);
            x = nextMove.getX();
            y = nextMove.getY();
            updateBounds();
        } else {
            returnToInitialPosition();
        }
    }

    private void updateBounds() {
        bounds = new Rectangle(x, y, width, height);
        setWidth(width);
        setHeight(height);
    }

    @Override
    public void render(GraphicRenderer graphic) {
        graphic.renderRectangle(bounds, textureRegion.getTexture());
    }

    public Vector2 getPosition() {
        return new Vector2(x, y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Rectangle updateRoom(Rectangle newRoom) {
        return currentRoom = newRoom;
    }

    public Rectangle getCurrentRoom() {
        return currentRoom;
    }
}