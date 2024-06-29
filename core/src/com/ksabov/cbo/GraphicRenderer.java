package com.ksabov.cbo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GraphicRenderer {
    private final SpriteBatch batch;

    public GraphicRenderer(SpriteBatch newBatch) {
        batch = newBatch;
    }

    public void renderRectangle(Rectangle rect, Texture texture) {
        batch.draw(texture, rect.x, rect.y, rect.width, rect.height);
    }

    public void renderRectangle(Rectangle rect, SpriteBatch batch, Texture texture) {
        batch.draw(texture, rect.x, rect.y, rect.width, rect.height);
    }

    public void render(Iterable<Rectangle> toRender, SpriteBatch batch, Texture texture) {
        for (Rectangle rectangle : toRender) {
            renderRectangle(rectangle, batch, texture);
        }
    }

    public void render(Iterable<? extends Drawable> toRender) {
        for (Drawable drawable : toRender) {
            drawable.render(this);
        }
    }
}
