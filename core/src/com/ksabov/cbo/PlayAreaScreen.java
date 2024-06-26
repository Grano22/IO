package com.ksabov.cbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.ksabov.cbo.behaviour.IntersectionDetector;
import com.ksabov.cbo.behaviour.Intersectional;
import com.ksabov.cbo.behaviour.TiledMapIntersectionDetector;
import com.ksabov.cbo.behaviour.UserControlReagent;
import com.ksabov.cbo.factory.*;
import com.ksabov.cbo.helper.ConsoleDebugger;
import com.ksabov.cbo.helper.MatrixDebugger;
import com.ksabov.cbo.helper.TiledMapHelper;
import com.ksabov.cbo.map.MapGameProperties;
import com.ksabov.cbo.map.TileFromMarkerMask;
import com.ksabov.cbo.map.TiledMapProjection;
import com.ksabov.cbo.objects.MetaPoint;
import com.ksabov.cbo.objects.Wall;

import java.lang.ref.WeakReference;
import java.util.*;

public class PlayAreaScreen extends BaseScreen {
    public Player player;
    final private OrthographicCamera levelCam;
    final private Stage stage;
    Texture dropImage;
    Sound dropSound;
    Music rainMusic;
    Rectangle bucket;
    Random generator = new Random();
    TiledMapProjection mapProjection;
    final MapProjectionFactory mapProjectionFactory;
    final TileFromMarkerMask tileFromMarkerMask;
    final MapFromProjectionFactory mapFromProjectionFactory;
    final RoomMarkersGenerator roomMarkersGenerator;
    final TiledMapHelper tiledMapHelper;
    final TiledMapIntersectionDetector tiledMapIntersectionDetector;

    private final RenderingMiddlewareManager renderingMiddlewareManager;

    private final IntersectionDetector intersectionDetector;
    private final Texture backgroundTexture = new Texture("main_map.png");
    private final Sprite backgroundSprite = new Sprite(backgroundTexture);

    // Map
    private TiledMap currentMap;
    private final RoomsFactory roomsFactory = new RoomsFactory();
    private final ActionableMapFinisher actionableMapFinisher = new ActionableMapFinisher();
    private final Map<String, Room> rooms = new HashMap<>();
    private OrthogonalTiledMapRenderer gameMapRenderer;

    // Draw
    Group group;

    private final InputMultiplexer inputMultiplexer;
    private final GameObjectCollection gameObjects;
    private final UserControlReagent userControlReagent;
    private final MapGenerationDefinition mapGenerationDefinition;

    private final Debugger debugger;

    private final WeakHashMap<Actor, Action> actions = new WeakHashMap<>();
    //private final Queue<Action> actionsQue = new PriorityQueue<>();

    public PlayAreaScreen(CBO game) {
        super(game, game.gameAssetsManager);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Rendering
        stage = new Stage();
        group = new Group();
        mapGenerationDefinition = new MapGenerationDefinition(70, 70, 40);
        renderingMiddlewareManager = new RenderingMiddlewareManager();
        mapProjectionFactory = new MapProjectionFactory();
        tileFromMarkerMask = new TileFromMarkerMask(game.gameAssetsManager);
        mapFromProjectionFactory = new MapFromProjectionFactory(game.gameAssetsManager, tileFromMarkerMask);
        roomMarkersGenerator = new RoomMarkersGenerator();
        tiledMapHelper = new TiledMapHelper();
        tiledMapIntersectionDetector = new TiledMapIntersectionDetector(tiledMapHelper);

        // Game objects
        gameObjects = new GameObjectCollection();

        // Camera
        levelCam = new OrthographicCamera();

        // Controls
        inputMultiplexer = new InputMultiplexer();
        userControlReagent = new UserControlReagent(levelCam);
        //inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(userControlReagent);

        // Interactions
        prepareMap(w, h);
        intersectionDetector = new IntersectionDetector(new WeakReference<>(gameObjects));

        // Debug
        debugger = new Debugger(game.getBatch().getProjectionMatrix());
    }

    private void prepareMap(float w, float h) {
        levelCam.setToOrtho(false, w, h);
        mapProjection = mapProjectionFactory.create(mapGenerationDefinition);

        // Map
        ArrayList<Vector2> roomsMarkers = roomMarkersGenerator.generate(mapProjection);
        ArrayList<Room> generatedRooms = roomsFactory.create(roomsMarkers);

        mapProjection = actionableMapFinisher.finish(mapProjection, generatedRooms);
        currentMap = mapFromProjectionFactory.create(mapProjection);


//        System.out.println("Floor layer =============================================");
//        MatrixDebugger.printMarkers(mapProjection.getRawMatrixMarkersLayer(0));
//        System.out.println("Wall layer =============================================");
//        MatrixDebugger.printMarkers(mapProjection.getRawMatrixMarkersLayer(1));
//        System.out.println("Items layer =============================================");
//        MatrixDebugger.printMarkers(mapProjection.getRawMatrixMarkersLayer(2));

        //currentMap.getLayers().add(roomsLayers);

        MapLayer objectsLayer = new MapLayer();
        //currentMap.getLayers().add(layerOfWalls);
        currentMap.getLayers().add(objectsLayer);
        gameMapRenderer = new OrthogonalTiledMapRenderer(currentMap);
        //gameMapRenderer.setView(guiCam);

        // Player
        player = new Player(new Vector2(200, 200), 30, 48);
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
                    MapActor nextMapObject = ((MapActor) mapObject);
                    renderingMiddlewareManager.beforeAddObject(nextMapObject.getActor());
                    group.addActor(nextMapObject.getActor());
                }
            }
        }

    }

    @Override
    public void show() {
    }

    private void handleMovement() {
        if (player.isRunning()) {
            System.out.println("tezt");
            return;
        }

        player.updateState(Player.State.RUNNING);

        MoveToAction moveAction = new MoveToAction();
        moveAction.setStartPosition(player.getX(), player.getY());
        moveAction.setPosition(player.getX(), player.getY());
        final float nextMoveSpeed = Player.MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
        boolean aIsPressed = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean dIsPressed = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean wIsPressed = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean sIsPressed = Gdx.input.isKeyPressed(Input.Keys.S);

        if (aIsPressed/* && !willCollide(new Vector2(moveAction.getX() - nextMoveSpeed, moveAction.getY()))*/) {
            moveAction.setX(moveAction.getX() - nextMoveSpeed);
        }

        if (dIsPressed/* && !willCollide(new Vector2(moveAction.getX() + nextMoveSpeed, moveAction.getY()))*/) {
            moveAction.setX(moveAction.getX() + nextMoveSpeed);
        }

        if (wIsPressed/* && !willCollide(new Vector2(moveAction.getX(), moveAction.getY() + nextMoveSpeed))*/) {
            moveAction.setY(moveAction.getY() + nextMoveSpeed);
        }

        if (sIsPressed/* && !willCollide(new Vector2(moveAction.getX(), moveAction.getY() - nextMoveSpeed))*/) {
            moveAction.setY(moveAction.getY() - nextMoveSpeed);
        }
        //actionsQue.add(moveAction);

        ArrayList<ArrayList<VisitedTiledMapDetails>> meetTiles = tiledMapIntersectionDetector.willStayOn(mapProjection, currentMap, moveAction);

        if (meetTiles.get(0).size() > 0) {
            System.out.println(meetTiles.get(0).size());
        }
        if (!meetTiles.get(1).isEmpty()) {
            VisitedTiledMapDetails lastBlockingVisitedArea = meetTiles.get(1).get(meetTiles.get(1).size() - 1);
            TiledMapTile lastBlockingTile = lastBlockingVisitedArea.getTile();
            Vector2 nextCollidedPosition = calculateCollisionPosition(moveAction, lastBlockingVisitedArea);
            moveAction.setPosition(nextCollidedPosition.x, nextCollidedPosition.y);
        }

        Intersectional intersectionObj = intersectionDetector.willIntersectWith(player, moveAction);
        if (intersectionObj != null) {
            System.out.println("intersectionObj");
            //return;
        }

        actions.put(player, moveAction);
        player.setPosition(moveAction.getX(), moveAction.getY());
        player.updateState(Player.State.STAYING);
    }

    private Vector2 calculateCollisionPosition(MoveToAction nextMove, VisitedTiledMapDetails lastBlockingTile) {
        float blockingElX = (Float)lastBlockingTile.getTile().getProperties().get(MapGameProperties.POSITION_X.toString());
        float blockingElY = (Float)lastBlockingTile.getTile().getProperties().get(MapGameProperties.POSITION_Y.toString());
        float fractionRate = 1;

//        List<Optional<TiledMapTile>> listOfTiled = Arrays.asList(
//            tiledMapHelper.getRightNeighbor(lastBlockingTile.getTile(), currentMap),
//            tiledMapHelper.getBottomNeighbor(lastBlockingTile.getTile(), currentMap),
//            tiledMapHelper.getLeftNeighbor(lastBlockingTile.getTile(), currentMap),
//            tiledMapHelper.getTopNeighbor(lastBlockingTile.getTile(), currentMap)
//        );

//        long countOfNeighborWalls = listOfTiled.stream()
//                .filter(Optional::isPresent)
//                .count();

        boolean[] listOfTiled = new boolean[]{
                tiledMapHelper.getRightNeighbor(lastBlockingTile.getTile(), currentMap).isPresent(),
                tiledMapHelper.getBottomNeighbor(lastBlockingTile.getTile(), currentMap).isPresent(),
                tiledMapHelper.getLeftNeighbor(lastBlockingTile.getTile(), currentMap).isPresent(),
                tiledMapHelper.getTopNeighbor(lastBlockingTile.getTile(), currentMap).isPresent()
        };

//        if (
//             !Arrays.equals(listOfTiled, new boolean[]{true, false, false, false}) &&
//             !Arrays.equals(listOfTiled, new boolean[]{false, true, false, true})
//        ) {
//            fractionRate = .5f;
//            System.out.println("right neighbor detected");
//        }
        float angle = getAngle(new Vector2(nextMove.getStartX(), nextMove.getStartY()), new Vector2(nextMove.getX(), nextMove.getY()));

        if (angle != 45) {
            fractionRate = .5f;
        }

        Vector2 nextPos = new Vector2(blockingElX - player.getWidth() * fractionRate, blockingElY - player.getHeight());

        if (nextMove.getStartX() > blockingElX) {
            nextPos.set(blockingElX + mapProjection.getTileSize() * fractionRate, nextPos.y);
        }

        if (nextMove.getStartY() > blockingElY) {
            nextPos.set(nextPos.x, blockingElY + mapProjection.getTileSize());
        }



        //float slope = (nextMove.getStartX() - blockingElX)/(nextMove.getStartY() - blockingElY);
        //System.out.println(slope);

        System.out.println(angle);
        //Vector2 nextPos = new Vector2(blockingElX - player.getWidth(), blockingElY - player.getHeight());



        return nextPos;
    }

    public float getAngle(Vector2 firstPoint, Vector2 secondPoint) {
        float angle = (float) Math.toDegrees(Math.atan2(secondPoint.y - firstPoint.y, secondPoint.x - firstPoint.x));

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }

//    public boolean willCollide(Vector2 nextPos)
//    {
//        AbstractMap.SimpleImmutableEntry<Integer, Integer> cords = tiledMapHelper.getTileIndexesByCords(mapProjection, nextPos.x, nextPos.y);
//        Optional<TiledMapTileLayer.Cell> tgCell  = Optional.ofNullable(((TiledMapTileLayer)currentMap.getLayers().get(1)).getCell(cords.getKey(), cords.getValue()));
//
//        if (!tgCell.isPresent()) {
//            return false;
//        }
//
//        Optional<TiledMapTile> tgTile = Optional.ofNullable(tgCell.get().getTile());
//
//        return tgTile.filter(tiledMapTile -> (boolean) tiledMapTile.getProperties().get(MapGameProperties.BLOCKING.toString())).isPresent();
//
//        //float blockingElX = (Float)tgTile.get().getProperties().get(MapGameProperties.POSITION_X.toString());
//        //float blockingElY = (Float)tgTile.get().getProperties().get(MapGameProperties.POSITION_Y.toString());
//
//    }

    private void handleDebug() {
//        gameCore.font.setColor(Color.GREEN);
//        gameCore.font.draw(gameCore.getBatch(), "Player position: " + player.getX() + ", " + player.getY(), 0, 480);

        debugger.setProjectionMatrix(gameCore.getBatch().getProjectionMatrix());
        debugger.debugCollision(player, Color.RED);
        for (Actor gameObject: gameObjects) {
            if (gameObject instanceof Intersectional) {
                Intersectional intersectional = (Intersectional) gameObject;
                debugger.debugCollision(intersectional, Color.BLUE);
            }
        }

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

        ConsoleDebugger.clear();
        //System.out.println(player.getX() + " " + player.getY());
        //System.out.println(tiledMapHelper.getTileCordsByCharacterPosition(mapProjection, player));

        MetaPoint finalObjective = (MetaPoint)gameObjects.getObjectByName("finalObjective");

        ScreenUtils.clear(0, 0, 0.2f, 1);
        Gdx.input.setInputProcessor(inputMultiplexer);

        gameCore.getBatch().begin();
        gameMapRenderer.setView(levelCam);
        gameMapRenderer.render();

        //guiCam.translate(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2);
        levelCam.position.set(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2, 0);
        levelCam.update();
        gameCore.getBatch().setProjectionMatrix(levelCam.combined);
        //guiCam.setToOrtho(false, (w / h) * 320, 320);
        updateObjectState();

        //backgroundSprite.draw(gameCore.getBatch());
        //finalObjective.debugSm();
        group.draw(gameCore.getBatch(), 0);
        finalObjective.debugSm(levelCam);
        //gameCore.getBatch().draw(null, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //stage.draw();
        //gameCore.getBatch().end();

        handleMovement();

        if (finalObjective.isHit()) {
            System.out.println("dssdsds");
            finalObjective.setHit(false);
            dispose();
            finishLevel();
            //FIXME: bugged
            prepareMap(w, h);
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

    private void finishLevel() {
        actions.clear();
    }

    @Override
    public void resize(int width, int height) {
        levelCam.viewportWidth = width;
        levelCam.viewportHeight = height;
        //stage.getViewport().update(width, height, true);
        levelCam.update();
    }

    @Override
    public void dispose() {
        intersectionDetector.dispose();
        dropImage.dispose();
        player.dispose();
        dropSound.dispose();
        rainMusic.dispose();
        //gameCore.getBatch().dispose();
    }
}
