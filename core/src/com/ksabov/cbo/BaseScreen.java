package com.ksabov.cbo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

abstract public class BaseScreen implements Screen {
    protected final CBO gameCore;
    protected final GameAssetsManager resourceManager;

    public BaseScreen(CBO gameCore, GameAssetsManager resourceManager) {
        this.gameCore = gameCore;
        this.resourceManager = resourceManager;
    }

    public TextButton createTextButton(String buttonName, float posX, float posY, Table table) {
        TextureRegion[][] playButtons = resourceManager.button;

        BitmapFont pixel10 = resourceManager.pixel10;

        TextureRegionDrawable imageUp = new TextureRegionDrawable(playButtons[0][0]);
        TextureRegionDrawable imageDown = new TextureRegionDrawable(playButtons[1][0]);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(imageUp, imageDown, null, pixel10);
        TextButton button = new TextButton(buttonName, buttonStyle);
        button.getLabel().setColor(new Color(79 / 255.f, 79 / 255.f, 117 / 255.f, 1));

        table.add(button).padLeft(posX).padTop(posY);
        table.row();

        return button;
    }

    public Table createTable() {
        Table table = new Table();
        table.setBounds(0,0, (float)Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight());

        return table;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        show();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
