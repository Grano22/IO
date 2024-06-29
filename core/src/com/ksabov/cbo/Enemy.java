package com.ksabov.cbo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Enemy implements Drawable {
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

    public Enemy(float x, float y, TextureRegion texture) {
        this.x = x;
        this.y = y;
        this.initialX = x;
        this.initialY = y;
        this.bounds = new Rectangle(x, y, width, height);
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
        this.x = initialX;
        this.y = initialY;
    }

    public boolean isAtInitialPosition() {
        return this.x == initialX && this.y == initialY;
    }

    public void followPlayer(Player player) {
        if (player.isInRoom()) {
            if (player.getPosition().x > this.x) this.x++;
            if (player.getPosition().x < this.x) this.x--;
            if (player.getPosition().y > this.y) this.y++;
            if (player.getPosition().y < this.y) this.y--;
            updateBounds();
        } else {
            returnToInitialPosition();
        }
    }

    private void updateBounds() {
        bounds = new Rectangle(x, y, width, height);
    }

    @Override
    public void render(GraphicRenderer graphic) {
        graphic.renderRectangle(bounds, textureRegion.getTexture());
    }
}