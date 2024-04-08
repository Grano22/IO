package com.ksabov.cbo;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MapActor extends MapObject {
    private final Actor actor;

    public MapActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor()
    {
        return actor;
    }
}

