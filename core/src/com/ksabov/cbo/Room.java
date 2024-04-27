package com.ksabov.cbo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ksabov.cbo.objects.Wall;

import java.util.ArrayList;
import java.util.Vector;

public class Room extends Actor {
    ArrayList<Wall> walls;

    public Room(String name, Vector2 position, int width, int height, ArrayList<Wall> walls) {
        setName(name);
        setPosition(position.x, position.y);
        setSize(width, height);
        this.walls = walls;
    }
}
