package com.ksabov.cbo.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.ksabov.cbo.GameAssetsManager;

import java.util.Collections;
import java.util.Map;

public class TileFromMarkerMask {
    public static final Map<MapGameProperties, Object> blockingProperty = Collections.singletonMap(MapGameProperties.BLOCKING, true);

    private final GameAssetsManager gameAssetsManager;

    public TileFromMarkerMask(GameAssetsManager gameAssetsManager) {
        this.gameAssetsManager = gameAssetsManager;
    }

    public TiledMapTile create(int markerMask, int tileSize) {
        TextureRegion textureRegionForTile = gameAssetsManager.getMapTextureRegion(markerMask);
        textureRegionForTile.setRegion(0, 0, tileSize, tileSize);
        StaticTiledMapTile nextCreatedTile = new StaticTiledMapTile(textureRegionForTile);
        MapProperties tileProperties = new MapProperties();

        if (markerMask == TiledMapProjection.MARKER_WALL || markerMask == TiledMapProjection.MARKER_ROOM_WALL_CORNER) {
            tileProperties.put(String.valueOf(MapGameProperties.BLOCKING), true);
        }

        nextCreatedTile.getProperties().putAll(tileProperties);

        return nextCreatedTile;
    }
}
