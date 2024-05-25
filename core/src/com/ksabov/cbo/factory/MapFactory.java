package com.ksabov.cbo.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

/**
 * @deprecated
 */
public class                                                                                                                                                                                                                            MapFactory {
    public TiledMap create(int mapWidth, int mapHeight, int tileWidth) {
        TiledMap newMap = new TiledMap();

        //Texture tiles = new Texture(Gdx.files.internal("brick_moss.jpg"));
        //TextureRegion[][] splitTiles = TextureRegion.split(tiles, tileWidth, tileHeight);
        Texture floorTexture = new Texture(Gdx.files.internal("basic_floor.png"));
        TextureRegion tile = new TextureRegion(floorTexture);
        tile.setRegion(0, 0, tileWidth, tileWidth);

        TiledMapTileLayer layer = new TiledMapTileLayer(mapWidth, mapHeight, tileWidth, tileWidth);
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                //Pixmap pixmap = new Pixmap(tileWidth, tileHeight, Pixmap.Format.RGBA8888);
                //pixmap.setColor(0, 1, 0, 0.75f);
                //pixmap.fillRectangle(0, 0, tileWidth, tileHeight);
                // new TextureRegion(new Texture(pixmap))

                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(tile));
                layer.setCell(x, y, cell);
            }
        }

        newMap.getLayers().add(layer);

        return newMap;
    }
}
