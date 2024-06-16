package com.ksabov.cbo.helper;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ksabov.cbo.map.TiledMapProjection;

import java.util.AbstractMap.SimpleImmutableEntry;

public final class TiledMapHelper {
    public SimpleImmutableEntry<Integer, Integer> getTileIndexesByCharacterPosition(TiledMapProjection mapProjection, Actor character) {
        SimpleImmutableEntry<Integer, Integer> tileCords = new SimpleImmutableEntry<>((int)Math.floor(character.getX() / mapProjection.getTileSize()), (int)Math.floor(character.getY() / mapProjection.getTileSize()));

        return tileCords;
    }

    public SimpleImmutableEntry<Integer, Integer> getTileIndexesByCords(TiledMapProjection mapProjection, float x, float y) {
        SimpleImmutableEntry<Integer, Integer> tileCords = new SimpleImmutableEntry<>((int)Math.floor(x / mapProjection.getTileSize()), (int)Math.floor(y / mapProjection.getTileSize()));

        return tileCords;
    }
}
