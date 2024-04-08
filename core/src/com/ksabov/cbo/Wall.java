package com.ksabov.cbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Wall extends Actor {
    private ShapeRenderer shapeRenderer;
    static private boolean projectionMatrixSet;

    public Wall(float x, float y, float width, float height) {
        shapeRenderer = new ShapeRenderer();
        projectionMatrixSet = false;
        this.setColor(Color.RED);
        this.setSize(width, height);
        this.setPosition(x, y);
        this.setTouchable(Touchable.enabled);
    }

    public void draw(Batch batch, float alpha) {
        batch.end();
        if(!projectionMatrixSet){
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        shapeRenderer.end();
        batch.begin();
    }

    public boolean overlaps(Rectangle playerHitbox)
    {
        return new Rectangle(getX(),getY(),getWidth(),getHeight()).overlaps(playerHitbox);
    }

//    @Override
//    public void render(SpriteBatch batch) {
//        batch.end();
//        if (shapeRenderer == null) {
//            shapeRenderer = new ShapeRenderer();
//        }
//
//        Gdx.gl.glEnable(GL20.GL_BLEND);
//        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
//        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
//        shapeRenderer.setColor(Color.RED);
//
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.rect(this.size.x, this.size.y , this.size.width, this.size.height);
//        shapeRenderer.end();
//        Gdx.gl.glDisable(GL20.GL_BLEND);
//
//        batch.begin();
//
////        Pixmap pixmap = new Pixmap( (int) this.size.x + (int) this.size.width, (int) this.size.y + (int) this.size.height, Pixmap.Format.RGBA8888 );
////        pixmap.setColor( 0, 1, 0, 0.75f );
////        pixmap.fillRectangle( 0, 0, (int) this.size.width, (int) this.size.height );
////        Texture pixmaptex = new Texture( pixmap );
////        Texture texture = this.createTexture((int) this.size.width, (int) this.size.height, Color.RED);
////        batch.draw(texture, this.size.x, this.size.y, this.size.width, this.size.height);
//    }

//    private Texture createTexture(int width, int height, Color color) {
//        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
//        pixmap.setColor(color);
//        pixmap.fillRectangle(0, 0, width, height);
//        Texture texture = new Texture(pixmap);
//        pixmap.dispose();
//
//        return texture;
//    }
}
