package com.ksabov.cbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

public class PlayAreaScreen extends BaseScreen {
    public Player player;
    final private OrthographicCamera guiCam;
    final private Stage stage;
    Texture dropImage;
    Sound dropSound;
    Music rainMusic;
    Rectangle bucket;
    long lastDropTime;
    int dropsGathered;

    private final Texture backgroundTexture = new Texture("main_map.png");
    private final Sprite backgroundSprite = new Sprite(backgroundTexture);

    private final MapFactory mapFactory = new MapFactory();
    private TiledMap currentMap;
    private OrthogonalTiledMapRenderer gameMapRenderer;

    Group group;

    private final GameObjectCollection gameObjects;

    public PlayAreaScreen(CBO game) {
        super(game, game.gameAssetsManager);

        //gameMapRenderer = new GameMapRenderer();
        currentMap = mapFactory.create();
        gameMapRenderer = new OrthogonalTiledMapRenderer(currentMap);
        gameObjects = new GameObjectCollection();
        this.stage = new Stage();

        group = new Group();
        player = new Player(new Vector2(320 /2 - 480 / 2, 20), 62, 62);

        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        dropSound.play();
        rainMusic.play();
        rainMusic.setLooping(true);

        guiCam = new OrthographicCamera(320, 480);
        guiCam.position.set(320f / 2f, 480f / 2f, 0);
    }

    @Override
    public void show() {
        //dropSound.stop();
        //rainMusic.stop();

        backgroundSprite.setPosition(-577, -560);

        gameCore.getBatch().begin();
        gameCore.font.draw(gameCore.getBatch(), "D:" + dropsGathered, 0, 480);

        group.addActor(new Wall(50, 170, 10, 300));
        group.addActor(player);


        // TODO: kolizja
//        Optional<Actor> actor = Optional.ofNullable(player.hit(player.getX(), player.getY(), false));
//        if (actor.isPresent()) {
//            System.out.println("test" +Gdx.graphics.getDeltaTime() );
//            System.out.println(actor.get().toString());
//        }

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
        Vector2 movementVector = new Vector2(player.getX(), player.getY());
        final float nextMoveSpeed = Player.MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.A) && handleCollision(player.getX() - nextMoveSpeed, player.getY())) {
            player.setX(player.getX() - nextMoveSpeed);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) && handleCollision(player.getX() + nextMoveSpeed, player.getY())) {
            player.setX(player.getX() + nextMoveSpeed);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) && handleCollision(player.getX(), player.getY() + nextMoveSpeed)) {
            player.setY(player.getY() + nextMoveSpeed);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S) && handleCollision(player.getX(), player.getY() - nextMoveSpeed)) {
            player.setY(player.getY() - nextMoveSpeed);
        }
    }

    private boolean handleCollision(float posX, float posY) {
        for (Actor renderedActor: group.getChildren()) {
            if (!(renderedActor instanceof Player)) {
                if (new Rectangle(posX, posY, player.getWidth(), player.getHeight()).overlaps(new Rectangle(renderedActor.getX(), renderedActor.getY(), renderedActor.getWidth(), renderedActor.getHeight()))) {
                    //System.out.println(renderedActor.getClass().getName());
                    return false;
                }
            }
        }

        return true;
    }

    private void handleDebug() {
        for (Actor renderedActor: group.getChildren()) {
            if (!(renderedActor instanceof Player)) {
                gameCore.debugHelper.drawDebugLine(
                    new Vector2(renderedActor.getX(), renderedActor.getY()),
                    new Vector2(renderedActor.getX(), renderedActor.getY() + renderedActor.getHeight()),
                    2,
                    Color.RED,
                    guiCam.combined
                );
            }
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        //handleDebug();
        gameCore.getBatch().setProjectionMatrix(guiCam.combined);
        guiCam.position.set(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2, 0);
        guiCam.update();

        backgroundSprite.draw(gameCore.getBatch());
        group.draw(gameCore.getBatch(), 0);
        //gameCore.getBatch().begin();
        //gameCore.getBatch().draw(null, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.draw();
        //gameCore.getBatch().end();


        gameMapRenderer.render();

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
