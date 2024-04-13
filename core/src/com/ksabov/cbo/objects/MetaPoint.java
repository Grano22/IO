package com.ksabov.cbo.objects;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MetaPoint extends Actor {
    boolean hit;

    public MetaPoint(Vector2 pos) {
        setPosition(pos.x, pos.y);
        setWidth(42);
        setHeight(42);
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean isHit() {
        return hit;
    }

    public void debugSm(Camera guiCam) {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(guiCam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(getX(), getY(), 42);
        shapeRenderer.end();
    }
}
