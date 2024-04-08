package com.ksabov.cbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class                                                                                                                                                                                                                            MapFactory {
    public TiledMap create() {
        TiledMap newMap = new TiledMap();

        final int tileWidth = 32;
        final int tileHeight = 32;
        final int mapWidth = 20;
        final int mapHeight = 20;

        Texture tiles = new Texture(Gdx.files.internal("brick_moss.jpg"));
        TextureRegion[][] splitTiles = TextureRegion.split(tiles, tileWidth, tileHeight);

        TiledMapTileLayer layer = new TiledMapTileLayer(mapWidth * tileWidth, mapHeight * tileHeight, tileWidth, tileHeight);
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                //Pixmap pixmap = new Pixmap(tileWidth, tileHeight, Pixmap.Format.RGBA8888);
                //pixmap.setColor(0, 1, 0, 0.75f);
                //pixmap.fillRectangle(0, 0, tileWidth, tileHeight);
                // new TextureRegion(new Texture(pixmap))

                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(splitTiles[x][y]));
                layer.setCell(x, y, cell);
            }
        }

        newMap.getLayers().add(layer);

        return newMap;
    }
}
