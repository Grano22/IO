package com.ksabov.cbo;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
        spawnRaindrop();

    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void show() {
    	Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top();

        TextButton playButton = new TextButton("Play", skin);
        TextButton optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new PlayScreen());
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
        rainMusic.play();
    }

    @Override
    public void render(float delta) {
         ScreenUtils.clear(0, 0, 0.2f, 1);

        // TODO: Remove later
        dropSound.stop();
        rainMusic.stop();

         guiCam.update();
         game.batch.setProjectionMatrix(guiCam.combined);

         game.batch.begin();
         game.font.draw(game.batch, "D:" + dropsGathered, 0, 480);


         this.group.addActor(new Wall(50, 170, 10, 300));
         this.group.addActor(player);

         this.group.draw(game.batch, 0);

         for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
         }
         game.batch.end();


         if (Gdx.input.isKeyPressed(Keys.A))
             player.setX(player.getX() - 200 * Gdx.graphics.getDeltaTime());
         if (Gdx.input.isKeyPressed(Keys.D))
             player.setX(player.getX() + 200 * Gdx.graphics.getDeltaTime());
         if (Gdx.input.isKeyPressed(Keys.W))
             player.setY(player.getY() + 200 * Gdx.graphics.getDeltaTime());
         if (Gdx.input.isKeyPressed(Keys.S))
             player.setY(player.getY() - 200 * Gdx.graphics.getDeltaTime());
        // TODO: kolizja
//        Optional<Actor> actor = Optional.ofNullable(player.hit(player.getX(), player.getY(), false));
//        if (actor.isPresent()) {
//            System.out.println("test" +Gdx.graphics.getDeltaTime() );
//            System.out.println(actor.get().toString());
//        }

        for (Actor renderedActor: group.getChildren()) {
            if (!(renderedActor instanceof Player)) {
                if (new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight()).overlaps(new Rectangle(renderedActor.getX(), renderedActor.getY(), renderedActor.getWidth(), renderedActor.getHeight()))) {
                    System.out.println(renderedActor.getClass().getName());
                }
            }
        }

        // Sprawdzanie czy postać nie jest po za mapą
//        if (bucket.x < 0)
//            bucket.x = 0;
//        if (bucket.x > 800 - 64)
//            bucket.x = 800 - 64;
//        if (bucket.y < 0)
//            bucket.y = 0;
//        if (bucket.y > 800 - 64)
//            bucket.y = 800 - 64;

         stage.draw();
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
