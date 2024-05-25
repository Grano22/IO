package com.ksabov.cbo.factory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.ksabov.cbo.GameAssetsManager;
import com.ksabov.cbo.TiledMapProjection;

public class MapFromProjectionFactory {
    private GameAssetsManager gameAssetsManager;

    public MapFromProjectionFactory(GameAssetsManager gameAssetsManager) {
        this.gameAssetsManager = gameAssetsManager;
    }

    public TiledMap create(TiledMapProjection projection) {
        TiledMap newMap = new TiledMap();

        for (int layerId = 0; layerId < projection.getMarkersLayersCount(); layerId++) {
            int[][] markers = projection.getRawMatrixMarkersLayer(layerId);
            TiledMapTileLayer layer = new TiledMapTileLayer(projection.getWidth(), projection.getHeight(), projection.getTileSize(), projection.getTileSize());

            for (int x = 0; x < projection.getWidth(); x++) {
                for (int y = 0; y < projection.getHeight(); y++) {
                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    TextureRegion textureRegionForTile = gameAssetsManager.getMapTextureRegion(markers[x][y]);
                    textureRegionForTile.setRegion(0, 0, projection.getTileSize(), projection.getTileSize());
                    cell.setTile(new StaticTiledMapTile(textureRegionForTile));
                    layer.setCell(x, y, cell);
                }
            }

            newMap.getLayers().add(layer);
        }

        return newMap;
    }
}
