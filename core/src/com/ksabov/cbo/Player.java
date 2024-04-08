package com.ksabov.cbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {
    final Texture skin;
    final static int MOVEMENT_SPEED = 200;

    final BodyDef body = new BodyDef();

    public Player(Vector2 position, int width, int height) {
        this.setPosition(position.x, position.y);
        this.setSize(width, height);
        this.skin = new Texture(Gdx.files.internal("hero_standard_pose.png"));
        body.type = BodyDef.BodyType.StaticBody;
        body.position.set(position.x + width/2, position.y + height/2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(skin, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public BodyDef getBody() {
        return body;
    }

    public void dispose() {
        skin.dispose();
    }
}
