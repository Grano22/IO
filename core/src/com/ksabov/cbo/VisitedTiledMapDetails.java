package com.ksabov.cbo;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class VisitedTiledMapDetails {
    private final TiledMapTile tile;
    private final MoveToAction step;

    public VisitedTiledMapDetails(TiledMapTile tile, MoveToAction step) {
        this.tile = tile;
        this.step = step;
    }

    public MoveToAction getStep() {
        return step;
    }

    public float getStepDistanceX() {
        return Math.abs(step.getX() - step.getStartX());
    }

    public float getStepDistanceY() {
        return Math.abs(step.getY() - step.getStartY());
    }

    public TiledMapTile getTile() {
        return tile;
    }
}
