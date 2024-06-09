package com.ksabov.cbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ksabov.cbo.behaviour.Collidable;

public class Player extends Actor implements Collidable {
    final Texture skin;
    final static int MOVEMENT_SPEED = 800;
    final Rectangle boundingRect;

    enum State {
        RUNNING
    }

    State currentState = State.RUNNING;

    public Player(Vector2 position, int width, int height) {
        super();
        this.setPosition(position.x, position.y);
        this.setSize(width, height);
        this.skin = new Texture(Gdx.files.internal("hero_standard_pose.png"));
        boundingRect = updateCollision();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(skin, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

//    public void drawDebug(ShapeRenderer renderer) {
//        renderer.rect(
//            this.getBoundingRectangle().getCenterX(),
//            this.getBoundingRectangle().getCenterY(),
//            this.getBoundingRectangle().getWidth(),
//            this.getBoundingRectangle().getHeight()
//        );
//    }

    public void dispose() {
        skin.dispose();
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        if (boundingRect != null) {
            boundingRect.set(updateCollision());
        }
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        if (boundingRect != null) {
            boundingRect.set(updateCollision());
        }
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if (boundingRect != null) {
            boundingRect.set(updateCollision());
        }
    }

    @Override
    public Rectangle getBoundingRect() {
        return boundingRect;
    }

    private Rectangle updateCollision() {
        float newWidth = getWidth();
        float newHeight = getHeight();

        return new Rectangle(getX(), getY(), newWidth, newHeight);
    }
}
