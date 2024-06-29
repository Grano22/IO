package com.ksabov.cbo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class StretchableDrawable extends TextureRegionDrawable {
    public StretchableDrawable(TextureRegion region) {
        super(region);
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        batch.draw(getRegion(), x, y, width, height);
    }
}