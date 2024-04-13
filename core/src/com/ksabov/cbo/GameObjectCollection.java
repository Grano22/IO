package com.ksabov.cbo;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashMap;

public class GameObjectCollection {
    private HashMap<String, Actor> actors;

    public GameObjectCollection() {
        actors = new HashMap<>();
    }

    public GameObjectCollection add(String name, Actor actor) {
        actors.put(name, actor);

        return this;
    }

    public Actor getObjectByName(String name) {
        return actors.get(name);
    }
}
