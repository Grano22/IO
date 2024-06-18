package com.ksabov.cbo.behaviour;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.ksabov.cbo.map.MapGameProperties;
import com.ksabov.cbo.map.TiledMapProjection;
import com.ksabov.cbo.helper.TiledMapHelper;

import java.util.*;
import java.util.AbstractMap.SimpleImmutableEntry;

public class TiledMapIntersectionDetector {
    final private TiledMapHelper tiledMapHelper;

    public TiledMapIntersectionDetector(TiledMapHelper tiledMapHelper) {
        this.tiledMapHelper = tiledMapHelper;
    }

    public Optional<TiledMapTile> getNextExactStep(TiledMapProjection tiledMapProjection, TiledMap map, MoveToAction moveToAction, Map<MapGameProperties, Object> labelPredicate) {
        SimpleImmutableEntry<Integer, Integer> tileMapLoc = tiledMapHelper.getTileIndexesByCords(tiledMapProjection, moveToAction.getX(), moveToAction.getY());
        TiledMapTileLayer decidableLayer = (TiledMapTileLayer) map.getLayers().get(1);
        TiledMapTile targetTile = decidableLayer.getCell(tileMapLoc.getKey(), tileMapLoc.getValue()).getTile();

        for (MapGameProperties nextPredicate : labelPredicate.keySet()) {
            if (
                    !targetTile.getProperties().containsKey(nextPredicate.toString()) ||
                            !targetTile.getProperties().get(nextPredicate.toString()).equals(labelPredicate.get(nextPredicate))
            ) {
                return Optional.empty();
            }
        }

        return Optional.of(targetTile);
    }

    public ArrayList<ArrayList<TiledMapTile>> willStayOn(TiledMapProjection tiledMapProjection, TiledMap map, MoveToAction moveToAction) {
        ArrayList<ArrayList<TiledMapTile>> meetTiles = new ArrayList<>();

        for (MapLayer mapLayer : map.getLayers()) {
            if (mapLayer instanceof TiledMapTileLayer) {
                ArrayList<TiledMapTile> meetLayerTiles = new ArrayList<>();
                meetTiles.add(meetLayerTiles);
                float nextPosX = moveToAction.getStartX(), nextPosY = moveToAction.getStartY();
                while (nextPosX < moveToAction.getX() || nextPosY < moveToAction.getY()) {
                    TiledMapTileLayer decidableLayer = (TiledMapTileLayer) mapLayer;
                    SimpleImmutableEntry<Integer, Integer> nextCords = tiledMapHelper.getTileIndexesByCords(tiledMapProjection, nextPosX, nextPosY);
                    Optional<TiledMapTileLayer.Cell> nextCell = Optional.ofNullable(decidableLayer.getCell(nextCords.getKey(), nextCords.getValue()));

                    if (!nextCell.isPresent()) {
                        break;
                    }

                    Optional<TiledMapTile> nextTile = Optional.ofNullable(nextCell.get().getTile());

                    if (!nextTile.isPresent()) {
                        break;
                    }

                    Optional<Object> isBlocker = Optional.ofNullable(nextTile.get().getProperties().get(MapGameProperties.BLOCKING.toString()));
                    if (isBlocker.isPresent() && isBlocker.get().equals(true)) {
                        break;
                    }

                    meetLayerTiles.add(nextTile.get());

                    //nextPosX += moveToAction.getX() - nextPosX < tiledMapProjection.getTileSize() ? moveToAction.getX() - nextPosX : tiledMapProjection.getTileSize();
                    nextPosX += moveToAction.getX() - nextPosX < tiledMapProjection.getTileSize() ? moveToAction.getX() - nextPosX : tiledMapProjection.getTileSize();
                    //nextPosY += moveToAction.getX() - nextPosY < tiledMapProjection.getTileSize() ? moveToAction.getY() - nextPosY : tiledMapProjection.getTileSize();
                    nextPosY += moveToAction.getX() - nextPosY < tiledMapProjection.getTileSize() ? moveToAction.getY() - nextPosY : tiledMapProjection.getTileSize();
                }
            }
        }

        return meetTiles;
    }
}
