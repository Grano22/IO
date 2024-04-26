package com.ksabov.cbo;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashMap;
import java.util.Iterator;

public class GameObjectCollection implements Iterable<Actor> {
    private final HashMap<String, Actor> actors;

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

    @Override
    public Iterator<Actor> iterator() {
        return actors.values().iterator();
    }
}
