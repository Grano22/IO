package com.ksabov.cbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.logging.Logger;

public class GameAssetsManager {
    private static final Logger LOGGER = Logger.getLogger(GameAssetsManager.class.getName());
    private static AssetManager assetManager = new AssetManager();

    // ATLAS
    public TextureAtlas atlas;

    // FONT
    public BitmapFont pixel10;

    // UI
    private final static String DEFAULT_UI_TEXTURE_ATLAS_PATH = "skins/default_ui/default_menu_ui.atlas";
    private final static String DEFAULT_UI_SKIN_PATH = "skins/default_ui/default_menu_ui.json";
    public static TextureAtlas DEFAULT_UI_TEXTURE_ATLAS = new TextureAtlas(DEFAULT_UI_TEXTURE_ATLAS_PATH);
    public static Skin DEFAULT_UI_SKIN = new Skin(Gdx.files.internal(DEFAULT_UI_SKIN_PATH), DEFAULT_UI_TEXTURE_ATLAS);

    // BUTTON
    public TextureRegion[][] button;

    public GameAssetsManager() {
        // ATLAS
        assetManager.load("skins/default_ui/default_menu_ui.atlas", TextureAtlas.class);
        assetManager.finishLoading();

        atlas = assetManager.get("skins/default_ui/default_menu_ui.atlas", TextureAtlas.class);

        // BUTTON
        button = new TextureRegion[][]{
                {
                        atlas.findRegion("checkbox"), //.split(80, 40);
                },
                {
                        atlas.findRegion("checkbox-on")
                }
        }; //.split(80, 40);

        // FONT
        pixel10 = new BitmapFont(); // new BitmapFont(Gdx.files.internal("skins/default_ui/font-export.fnt"), atlas.findRegion("pixel"), false);
    }
}
