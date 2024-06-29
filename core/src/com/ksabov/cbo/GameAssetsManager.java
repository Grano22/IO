package com.ksabov.cbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class GameAssetsManager implements Disposable {
    public static int BASIC_FLOOR = 1;
    public static int BASIC_WALL = 2;
    public static int ENEMY_WALKING = 41;
    public static int ARROW_UP_DEBUG = 991;
    public static int WALL_DEBUG = 992;

    private static final Logger LOGGER = Logger.getLogger(GameAssetsManager.class.getName());
    private static AssetManager assetManager = new AssetManager();

    // ATLAS
    public TextureAtlas atlas;

    // FONT
    public BitmapFont pixel10;

    // UI
    private final static String DEFAULT_UI_TEXTURE_ATLAS_PATH = "skins/default_ui/default_menu_ui.atlas";
    private final static String DEFAULT_UI_SKIN_PATH = "skins/default_ui/default_menu_ui.json";
    //public static TextureAtlas DEFAULT_UI_TEXTURE_ATLAS = new TextureAtlas(DEFAULT_UI_TEXTURE_ATLAS_PATH);
    //public static Skin DEFAULT_UI_SKIN = new Skin(Gdx.files.internal(DEFAULT_UI_SKIN_PATH), DEFAULT_UI_TEXTURE_ATLAS);

    // BUTTON
    public TextureRegion[][] button;

    // MAP TEXTURE REGIONS
    private final Map<Integer, TextureRegion> textureAssets = new HashMap<>();

    public GameAssetsManager() {
        // ATLAS
//        assetManager.load("skins/default_ui/default_menu_ui.atlas", TextureAtlas.class);
//        assetManager.finishLoading();
//
//        atlas = assetManager.get("skins/default_ui/default_menu_ui.atlas", TextureAtlas.class);

        // BUTTON
//        button = new TextureRegion[][]{
//                {
//                        atlas.findRegion("checkbox"), //.split(80, 40);
//                },
//                {
//                        atlas.findRegion("checkbox-on")
//                }
//        }; //.split(80, 40);

        // FONT
        //pixel10 = new BitmapFont(); // new BitmapFont(Gdx.files.internal("skins/default_ui/font-export.fnt"), atlas.findRegion("pixel"), false);

        // REGIONS
        loadTextureRegions();
    }

    private void loadTextureRegions() {
        Texture floorTexture = new Texture("basic_floor.png");
        TextureRegion floorRegion = new TextureRegion(floorTexture);

        textureAssets.put(BASIC_FLOOR, floorRegion);

        Texture texture = new Texture("wall.jpg");
        TextureRegion wallRegion = new TextureRegion(texture);

        textureAssets.put(BASIC_WALL, wallRegion);

        Texture dustWallTexture = new Texture("wall_debug.jpg");
        TextureRegion dustWallRegion = new TextureRegion(dustWallTexture);

        textureAssets.put(WALL_DEBUG, dustWallRegion);

        Texture doorTexture = new Texture("arrow_up_debug.png");
        TextureRegion doorDebugRegion = new TextureRegion(doorTexture);
        textureAssets.put(ARROW_UP_DEBUG, doorDebugRegion);

        Texture enemyTexture = new Texture("enemy_walking.png");
        TextureRegion enemyTextureRegion = new TextureRegion(enemyTexture);
        textureAssets.put(ENEMY_WALKING, enemyTextureRegion);
    }

    public TextureRegion getMapTextureRegion(int kind) {
        return textureAssets.get(kind);
    }

    @Override
    public void dispose() {
        for (TextureRegion textureRegion: textureAssets.values()) {
            textureRegion.getTexture().dispose();
        }

        textureAssets.clear();
    }
}
