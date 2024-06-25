package com.ksabov.cbo.helper;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ksabov.cbo.map.MapGameProperties;
import com.ksabov.cbo.map.TiledMapProjection;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Optional;

public final class TiledMapHelper {
    public SimpleImmutableEntry<Integer, Integer> getTileIndexesByCharacterPosition(TiledMapProjection mapProjection, Actor character) {
        SimpleImmutableEntry<Integer, Integer> tileCords = new SimpleImmutableEntry<>((int)Math.floor(character.getX() / mapProjection.getTileSize()), (int)Math.floor(character.getY() / mapProjection.getTileSize()));

        return tileCords;
    }

    public SimpleImmutableEntry<Integer, Integer> getTileIndexesByCords(TiledMapProjection mapProjection, float x, float y) {
        SimpleImmutableEntry<Integer, Integer> tileCords = new SimpleImmutableEntry<>((int)Math.floor(x / mapProjection.getTileSize()), (int)Math.floor(y / mapProjection.getTileSize()));

        return tileCords;
    }

    public Optional<TiledMapTile> getRightNeighbor(TiledMapTile tile, TiledMap map) {
        int tileLayerId = (int) tile.getProperties().get(MapGameProperties.LAYER_ID.toString());
        return Optional
                .ofNullable(((TiledMapTileLayer) map.getLayers().get(tileLayerId)).getCell((int) tile.getOffsetX() + 1, (int) tile.getOffsetY()))
                .map(TiledMapTileLayer.Cell::getTile)
        ;
    }

    public Optional<TiledMapTile> getBottomNeighbor(TiledMapTile tile, TiledMap map) {
        int tileLayerId = (int) tile.getProperties().get(MapGameProperties.LAYER_ID.toString());
        return Optional
                .ofNullable(((TiledMapTileLayer) map.getLayers().get(tileLayerId)).getCell((int) tile.getOffsetX(), (int) tile.getOffsetY() - 1))
                .map(TiledMapTileLayer.Cell::getTile)
        ;
    }

    public Optional<TiledMapTile> getLeftNeighbor(TiledMapTile tile, TiledMap map) {
        int tileLayerId = (int) tile.getProperties().get(MapGameProperties.LAYER_ID.toString());
        return Optional
                .ofNullable(((TiledMapTileLayer) map.getLayers().get(tileLayerId)).getCell((int) tile.getOffsetX() - 1, (int) tile.getOffsetY()))
                .map(TiledMapTileLayer.Cell::getTile)
        ;
    }

    public Optional<TiledMapTile> getTopNeighbor(TiledMapTile tile, TiledMap map) {
        int tileLayerId = (int) tile.getProperties().get(MapGameProperties.LAYER_ID.toString());
        return Optional
                .ofNullable(((TiledMapTileLayer) map.getLayers().get(tileLayerId)).getCell((int) tile.getOffsetX(), (int) tile.getOffsetY() + 1))
                .map(TiledMapTileLayer.Cell::getTile)
          ;
    }
}
