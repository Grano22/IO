package com.ksabov.cbo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class ItemsRenderer {
    public void renderExitPoint(Rectangle exitPoint, SpriteBatch batch, Texture exitTexture) {
        batch.draw(exitTexture, exitPoint.x, exitPoint.y, exitPoint.width, exitPoint.height);
    }
}
