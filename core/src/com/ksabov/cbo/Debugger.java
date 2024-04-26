package com.ksabov.cbo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.ksabov.cbo.behaviour.Intersectional;

public class Debugger {
    private final ShapeRenderer debugRenderer;

    public Debugger(Matrix4 projection) {
         debugRenderer = new ShapeRenderer();
         debugRenderer.setProjectionMatrix(projection);
    }

    public void setProjectionMatrix(Matrix4 projection) {
        debugRenderer.setProjectionMatrix(projection);
    }

    public void debugCollision(Intersectional intersectional, Color color) {
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(color);
        Rectangle boundingRect = intersectional.getBoundingRect();
        debugRenderer.rect(boundingRect.x, boundingRect.y, boundingRect.getWidth(), boundingRect.getHeight());
        debugRenderer.end();
    }
}
