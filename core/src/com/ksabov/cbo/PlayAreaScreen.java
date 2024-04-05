package com.ksabov.cbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class PlayAreaScreen extends BaseScreen {
    public Player player;
    final private OrthographicCamera guiCam;
    final private Stage stage;
    protected Skin skin;
    protected TextureAtlas atlas;
    Texture dropImage;
    Sound dropSound;
    Music rainMusic;
    Rectangle bucket;
    long lastDropTime;
    int dropsGathered;

    Group group;

    public PlayAreaScreen(CBO game) {
        super(game, game.gameAssetsManager);

        this.stage = new Stage();

        group = new Group();
        player = new Player(new Vector2(320 /2 - 480 / 2, 20), 194, 64);

        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);

        guiCam = new OrthographicCamera(320, 480);
        guiCam.position.set(320f / 2f, 480f / 2f, 0);
    }

    @Override
    public void show() {
        dropSound.stop();
        rainMusic.stop();
        gameCore.getBatch().setProjectionMatrix(guiCam.combined);

        gameCore.getBatch().begin();
        gameCore.font.draw(gameCore.getBatch(), "D:" + dropsGathered, 0, 480);

        this.group.addActor(new Wall(50, 170, 10, 300));
        this.group.addActor(player);


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

    }

    private void handleMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            LOGGER.info("Pressed A");
            player.setX(player.getX() - 200 * Gdx.graphics.getDeltaTime());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setX(player.getX() + 200 * Gdx.graphics.getDeltaTime());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.setY(player.getY() + 200 * Gdx.graphics.getDeltaTime());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setY(player.getY() - 200 * Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        guiCam.update();

        group.draw(gameCore.getBatch(), 0);
        //gameCore.getBatch().begin();
        //gameCore.getBatch().draw(null, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.draw();
        //gameCore.getBatch().end();
        handleMovement();
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        player.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }
}
