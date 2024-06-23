package com.ksabov.cbo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ksabov.cbo.objects.Wall;

import java.util.ArrayList;
import java.util.Vector;

public class Room {
    final private String name;
    final private Vector2 position;
    final private float width;
    final private float height;

    public Room(String name, Vector2 position, float width, float height) {
        this.name = name;
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public String getName() {
        return name;
    }

    public Vector2 getPosition() {
        return position;
    }
}
