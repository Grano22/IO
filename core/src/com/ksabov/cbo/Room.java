package com.ksabov.cbo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ksabov.cbo.objects.Wall;

import java.util.ArrayList;
import java.util.Vector;

public class Room {
    final String name;
    final Vector2 position;
    final float width;
    final float height;

    public Room(String name, Vector2 position, float width, float height) {
        this.name = name;
        this.position = position;
        this.width = width;
        this.height = height;
    }
}
