package com.ksabov.cbo.factory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.ksabov.cbo.GameAssetsManager;
import com.ksabov.cbo.map.MapGameProperties;
import com.ksabov.cbo.map.TileFromMarkerMask;
import com.ksabov.cbo.map.TiledMapProjection;

public class MapFromProjectionFactory {
    private final GameAssetsManager gameAssetsManager;
    private final TileFromMarkerMask tileFromMarkerMask;

    public MapFromProjectionFactory(GameAssetsManager gameAssetsManager, TileFromMarkerMask tileFromMarkerMask) {
        this.gameAssetsManager = gameAssetsManager;
        this.tileFromMarkerMask = tileFromMarkerMask;
    }

    public TiledMap create(TiledMapProjection projection) {
        TiledMap newMap = new TiledMap();

        for (int layerId = 0; layerId < projection.getMarkersLayersCount(); layerId++) {
            int[][] markers = projection.getRawMatrixMarkersLayer(layerId);
            TiledMapTileLayer layer = new TiledMapTileLayer(projection.getWidth(), projection.getHeight(), projection.getTileSize(), projection.getTileSize());

            for (int x = 0; x < projection.getWidth(); x++) {
                for (int y = 0; y < projection.getHeight(); y++) {
                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    TiledMapTile nextCreatedTile = tileFromMarkerMask.create(markers[x][y], projection.getTileSize());
                    nextCreatedTile.getProperties().put(MapGameProperties.POSITION_X.toString(), (float)x * projection.getTileSize());
                    nextCreatedTile.getProperties().put(MapGameProperties.POSITION_Y.toString(), (float)y * projection.getTileSize());
                    cell.setTile(nextCreatedTile);
                    layer.setCell(x, y, cell);
                }
            }

            newMap.getLayers().add(layer);
        }

        return newMap;
    }
}
