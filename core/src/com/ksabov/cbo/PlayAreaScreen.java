package com.ksabov.cbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.ksabov.cbo.behaviour.UserControlReagent;
import com.ksabov.cbo.factory.MapBoundariesFactory;
import com.ksabov.cbo.factory.MapFactory;
import com.ksabov.cbo.objects.MetaPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class PlayAreaScreen extends BaseScreen {
    public Player player;
    final private OrthographicCamera guiCam;
    final private Stage stage;
    Texture dropImage;
    Sound dropSound;
    Music rainMusic;
    Rectangle bucket;
    long lastDropTime;
    Random generator = new Random();


    private final Texture backgroundTexture = new Texture("main_map.png");
    private final Sprite backgroundSprite = new Sprite(backgroundTexture);

    private final MapFactory mapFactory = new MapFactory();
    private TiledMap currentMap;
    private OrthogonalTiledMapRenderer gameMapRenderer;

    Group group;

    private final InputMultiplexer inputMultiplexer;

    private final GameObjectCollection gameObjects;

    private final UserControlReagent userControlReagent;

    private final MapGenerationDefinition mapGenerationDefinition;

    public PlayAreaScreen(CBO game) {
        super(game, game.gameAssetsManager);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Rendering
        stage = new Stage();
        group = new Group();
        mapGenerationDefinition = new MapGenerationDefinition(1000, 1000, 42);

        // Camera
        guiCam = new OrthographicCamera();

        // Controls
        inputMultiplexer = new InputMultiplexer();
        userControlReagent = new UserControlReagent(guiCam);
        //inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(userControlReagent);

        gameObjects = parepareMap(w, h);
    }

    private GameObjectCollection parepareMap(float w, float h) {
        final GameObjectCollection gameObjects;
        guiCam.setToOrtho(false, w, h);

        // Walls
        //MapActor randomWall = new MapActor(new Wall(generator.nextInt(1025), generator.nextInt(1025), 32, 32));
        // Walls should have to types: vertical and horizontal
        //*up Myśl* Taka myśl żeby podzielić ściany na idące pionowo i poziomo (chodzi o value width i height żeby to jakoś ogarnąć bo jedne muszą mieć statycznie wysokość a drugie szerokość)
        //*************************************Wazna Uwaga GRACJAN*****************************************************
        // postać przechodzi przez ściany tylko te ktore maja ujemnego height lub width (renderują się w dół lub w lewo)
        //ToDo
        //naprawa kolizji z postacią dla ścian z wartością ujemną
        //dodanie sprawdzenia kolizji dla ścian aby kończył renderowanie w momencie napotkania przeszkody
        //dodanie sprawdzenia czy nie renderuje się na istniejącym obiekcie
        //dodanie drzwi *na potem*
        int wall_h = 32;
        int wall_w = 32;
        MapLayer layerOfWalls = new MapLayer();
        MapLayer objectsLayer = new MapLayer();
        //How many objects (walls) should be created
        for(int i=0; i<(generator.nextInt(10)+7); i++) {
            float x = generator.nextInt(1025);
            float y = generator.nextInt(1025);
            //Generating in how many directions from our start point the wall will go (min 1 max 4)
            for(int j=0; j<generator.nextInt(4)+1; ++j) {
                //Drawing direction
                int l = (generator.nextInt(4)+1);
                switch (l) {
                    case 1:
                        while(wall_h != 256){
                            wall_h += 8;
                        }
                        System.out.println("x"+x+"y"+y+"wall_h + "+wall_h);
                       MapActor randomWall = new MapActor(new Wall(x, y, 32, wall_h));
                        layerOfWalls.getObjects().add(randomWall);
//                        wall_list.add(randomWall1);
                        break;
                    case 2:
                        while(wall_w != 256){
                            wall_w += 8;
                        }
                        System.out.println("x"+x+"y"+y+"wall_w + "+wall_w);
                        MapActor randomWall2 = new MapActor(new Wall(x, y, wall_w, 32));
                        layerOfWalls.getObjects().add(randomWall2);
//                        wall_list.add(randomWall2);
                        break;
                    case 3:
                        while(wall_w != -256){
                            wall_w -= 8;
                        }
                        System.out.println("x"+x+"y"+y+"wall_w - "+wall_w);
                        MapActor randomWall3 = new MapActor(new Wall(x, y, wall_w, 32));
                        layerOfWalls.getObjects().add(randomWall3);
 //                       wall_list.add(randomWall3);
                        break;
                    case 4:
                        while(wall_h != -256){
                            wall_h -= 8;
                        }
                        System.out.println("x"+x+"y"+y+"wall_h - "+wall_h);
                        MapActor randomWall4 = new MapActor(new Wall(x, y, 32, wall_h));
                        layerOfWalls.getObjects().add(randomWall4);
  //                      wall_list.add(randomWall4);
                        break;
                    default:
                        break;
                }
                //MapActor randomWall = new MapActor(new Wall(x, y, wall_w, wall_h));
                //wall_list.add(randomWall);
                //layerOfWalls.getObjects().add(randomWall);
            }
           // MapActor randomWall = new MapActor(new Wall(x, y, wall_w, wall_h));
           // layerOfWalls.getObjects().add(randomWall);
        }

        // Map

        //gameMapRenderer = new GameMapRenderer();
        currentMap = mapFactory.create(mapGenerationDefinition.mapWidth(), mapGenerationDefinition.mapHeight(), mapGenerationDefinition.tileSize());
        currentMap.getLayers().add(MapBoundariesFactory.createMapBoundaries(0, 0, mapGenerationDefinition.mapWidth(), mapGenerationDefinition.mapHeight()));
        currentMap.getLayers().add(layerOfWalls);
        currentMap.getLayers().add(objectsLayer);
        gameMapRenderer = new OrthogonalTiledMapRenderer(currentMap);
        //gameMapRenderer.setView(guiCam);

        // Game objects
        gameObjects = new GameObjectCollection();

        // Player
        player = new Player(new Vector2(200, 200), 62, 62);
        group.addActor(player);

        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
//        dropSound.play();
//        rainMusic.play();
        rainMusic.setLooping(true);

        backgroundSprite.setPosition(-577, -560);

        //MetaPoint finalObjective = new MetaPoint(new Vector2((float)(Math.random() * mapWidth), (float)(Math.random() * mapHeight)));
        MetaPoint finalObjective = new MetaPoint(new Vector2(300, 300));
        gameObjects.add("finalObjective", finalObjective);
        objectsLayer.getObjects().add(
            new MapActor(finalObjective)
        );

        for (MapLayer layer: currentMap.getLayers()) {
            for (MapObject mapObject: layer.getObjects()) {
                if (mapObject instanceof MapActor) {
                    group.addActor(((MapActor)mapObject).getActor());
                }
            }
        }

        return gameObjects;
    }

    @Override
    public void show() {
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
                Rectangle playerCollision = new Rectangle(Math.abs(posX + 10), Math.abs(posY - 10), player.getWidth() - 10, player.getHeight() - 10);
                Rectangle objectCollision = new Rectangle(Math.abs(renderedActor.getX()), Math.abs(renderedActor.getY()), renderedActor.getWidth(), renderedActor.getHeight());
                if (renderedActor instanceof Wall && playerCollision.overlaps(objectCollision)) {
                    //System.out.println(renderedActor.getClass().getName());
                    return false;
                }
            }
        }

        return true;
    }

    private void handleDebug() {
        gameCore.font.setColor(Color.GREEN);
        gameCore.font.draw(gameCore.getBatch(), "Player position: " + player.getX() + ", " + player.getY(), 0, 480);

//        for (Actor renderedActor: group.getChildren()) {
//            if (!(renderedActor instanceof Player)) {
//                gameCore.debugHelper.drawDebugLine(
//                    new Vector2(renderedActor.getX(), renderedActor.getY()),
//                    new Vector2(renderedActor.getX(), renderedActor.getY() + renderedActor.getHeight()),
//                    2,
//                    Color.RED,
//                    guiCam.combined
//                );
//            }
//        }
    }

    @Override
    public void render(float delta) {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        MetaPoint finalObjective = (MetaPoint)gameObjects.getObjectByName("finalObjective");

        ScreenUtils.clear(0, 0, 0.2f, 1);
        Gdx.input.setInputProcessor(inputMultiplexer);

        gameCore.getBatch().begin();
        gameMapRenderer.setView(guiCam);
        gameMapRenderer.render();

        //guiCam.translate(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2);
        guiCam.position.set(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2, 0);
        guiCam.update();
        gameCore.getBatch().setProjectionMatrix(guiCam.combined);
        //guiCam.setToOrtho(false, (w / h) * 320, 320);
        updateObjectState();

        //backgroundSprite.draw(gameCore.getBatch());
        //finalObjective.debugSm();
        group.draw(gameCore.getBatch(), 0);
        finalObjective.debugSm(guiCam);
        //gameCore.getBatch().draw(null, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //stage.draw();
        //gameCore.getBatch().end();

        handleMovement();

        if (finalObjective.isHit()) {
            System.out.println("dssdsds");
            //FIXME: bugged parepareMap(w, h);
        }

        handleDebug();

        gameCore.getBatch().end();
    }

    private void updateObjectState() {
        Rectangle playerCollision = new Rectangle(Math.abs(player.getX() + 10), Math.abs(player.getY() - 10), player.getWidth() - 10, player.getHeight() - 10);

        for (MapLayer layer: currentMap.getLayers()) {
            for (MapObject mapObject: layer.getObjects()) {
                if (mapObject instanceof MapActor) {
                    final Actor actorType = ((MapActor) mapObject).getActor();

                    if (actorType instanceof MetaPoint) {
                        final MetaPoint metaPoint = (MetaPoint) actorType;
                        Rectangle objectSpace = new Rectangle(Math.abs(metaPoint.getX()), Math.abs(metaPoint.getY()), metaPoint.getWidth(), metaPoint.getHeight());
                        metaPoint.setHit(playerCollision.overlaps(objectSpace));
                    }
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        guiCam.viewportWidth = width;
        guiCam.viewportHeight = height;
        //stage.getViewport().update(width, height, true);
        guiCam.update();
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        player.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }
}
