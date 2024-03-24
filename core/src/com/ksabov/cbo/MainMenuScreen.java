package com.ksabov.cbo;

import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.*;
import java.util.Objects;
import java.util.Optional;

public class MainMenuScreen implements Screen {
    public Player player;
    final private CBO game;
    final private OrthographicCamera guiCam;
    final private Stage stage;
    protected Skin skin;
    protected TextureAtlas atlas;
    Texture dropImage;
    Sound dropSound;
    Music rainMusic;
    Rectangle bucket;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;

    Group group;

    public MainMenuScreen(CBO game) {
        this.game = game;
        this.stage = new Stage();

        group = new Group();
        player = new Player(new Vector2(320 /2 - 480 / 2, 20), 194, 64);

        dropImage = new Texture(Gdx.files.internal("droplet.png"));


        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);

        guiCam = new OrthographicCamera(320, 480);
        guiCam.position.set(320f / 2f, 480f / 2f, 0);
//        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
//        textButtonStyle.font = font;
//        TextButton gameButton = new TextButton("New game", textButtonStyle);
        //stage.addActor(gameButton);

        raindrops = new Array<Rectangle>();
        //atlas = new TextureAtlas("skin.atlas");
        //skin = new Skin(Gdx.files.internal("skin.json"), atlas);
    }

    @Override
    public void show() {
    	Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top();

        //Texture tex = new Texture(Gdx.files.internal("bucket.png"));

        String name = "";
        Texture normalTexture = new Texture(Gdx.files.internal(name + "droplet.png"));
        Texture pressedTexture = new Texture(Gdx.files.internal(name + "bucket.png"));
        Drawable normalDrawable = new TextureRegionDrawable(normalTexture);
        Drawable pressedDrawable = new TextureRegionDrawable(pressedTexture);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = normalDrawable;
        style.down = pressedDrawable;
        BitmapFont font = new BitmapFont();
        font.setColor(0.5f,0.4f,0,1);
        style.font = font;

        TextButton playButton = new TextButton("Play", style);
        TextButton optionsButton = new TextButton("Options", style);
        TextButton exitButton = new TextButton("Exit", style);

        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new PlayScreen(game));
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        mainTable.add(playButton);
        mainTable.row();
        mainTable.add(optionsButton);
        mainTable.row();
        mainTable.add(exitButton);

        stage.addActor(mainTable);
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
        dropImage.dispose();
        player.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }
}
