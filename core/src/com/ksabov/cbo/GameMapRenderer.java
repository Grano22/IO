package com.ksabov.cbo;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.World;

public class GameMapRenderer implements MapRenderer {
    private OrthographicCamera camera;
    private Map map;

    public GameMapRenderer() {

    }

    @Override
    public void setView(OrthographicCamera camera) {
        camera.setToOrtho(false, 30, 20);
        this.camera = camera;
    }

    @Override
    public void setView(Matrix4 projectionMatrix, float viewboundsX, float viewboundsY, float viewboundsWidth, float viewboundsHeight) {

    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public void render() {
        for (MapLayer layer: map.getLayers()) {
            for (MapObject mapObject: layer.getObjects()) {
                
            }
        }
    }

    @Override
    public void render(int[] layers) {

    }
}
