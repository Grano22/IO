package com.ksabov.cbo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;

import java.util.Optional;

public class CBO extends ApplicationAdapter {
    private final RoomOperator roomOperator = new RoomOperator();
    private SpriteBatch batch;
    private Texture wallTexture;
    private Texture roomTexture;
    private Texture doorTexture;
    private Texture itemTexture;
    private BitmapFont font;
    private RoomGenerator roomGenerator;
    private ItemsGenerator itemsGenerator;
    private GraphicRenderer graphic;
    private RoomsUtils roomsUtils;
    private Player chatPlayer;
    private OrthographicCamera camera;
    private Texture exitTexture;
    private Texture enemyTexture;
    private OrthographicCamera hudCamera; // Separate camera for HUD
    private GameAssetsManager gameAssetsManager;

    private Rectangle exitPoint;

    @Override
    public void create() {
        batch = new SpriteBatch();
        graphic = new GraphicRenderer(batch);
        wallTexture = new Texture("wall.jpg");
        roomTexture = new Texture("main_map.png");
        doorTexture = new Texture("door.png");
        itemTexture = new Texture("item.png");
        exitTexture = new Texture("exit.png");
        font = new BitmapFont(); // Default font
        gameAssetsManager = new GameAssetsManager();

        roomGenerator = new RoomGenerator(gameAssetsManager);
        roomsUtils = new RoomsUtils(roomGenerator.getRooms());
        itemsGenerator = new ItemsGenerator();

        // Example initial player position
        float playerX = roomGenerator.getRooms().get(0).x + roomGenerator.getRooms().get(0).width / 2;
        float playerY = roomGenerator.getRooms().get(0).y + roomGenerator.getRooms().get(0).height / 2;
        chatPlayer = new Player(new Texture("hero_standard_pose.png"), playerX, playerY, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT, 200); // Reduced player size
        Rectangle randomizedRoom = roomOperator.randomizeRoom(roomGenerator.getRooms());
        chatPlayer.moveToRoom(randomizedRoom);

        Optional<Rectangle> exitPointResult = itemsGenerator.generateExitPoint(chatPlayer, roomsUtils);

        if (!exitPointResult.isPresent()) {
            System.out.println("Cannot generate exit point");
            System.exit(1);
        }

        exitPoint = exitPointResult.get();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(chatPlayer.getPosition().x + chatPlayer.getBounds().width / 2, chatPlayer.getPosition().y + chatPlayer.getBounds().height / 2, 0);
        camera.update();

        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCamera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        hudCamera.update();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean scrolled(float amountX, float amountY) {
                camera.zoom += amountY * 0.1f;
                return true;
            }
        });
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        chatPlayer.update(delta, roomGenerator.getWalls(), roomGenerator.getDoors(), roomGenerator.getItems(), roomGenerator); // Pass doors and items list to player update

        camera.position.set(chatPlayer.getPosition().x + chatPlayer.getBounds().width / 2, chatPlayer.getPosition().y + chatPlayer.getBounds().height / 2, 0);
        camera.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        if (chatPlayer.getBounds().overlaps(exitPoint)) {
            roomGenerator = new RoomGenerator(gameAssetsManager);
            chatPlayer.setPoints(0);
            Rectangle randomizedRoom = roomOperator.randomizeRoom(roomGenerator.getRooms());
            chatPlayer.moveToRoom(randomizedRoom);
            Rectangle farthestRoom = roomsUtils.getFarthestRoomToThePlayer(chatPlayer);
            exitPoint.setPosition(farthestRoom.x, farthestRoom.y);
        }

        batch.begin();
        // Render walls
        roomGenerator.renderWalls(batch, wallTexture);
        // Render rooms
        roomGenerator.renderRooms(batch, roomTexture);
        // Render player
        chatPlayer.render(batch);
        // Render doors
        roomGenerator.renderDoors(batch, doorTexture);
        // Render items
        roomGenerator.renderItems(batch, itemTexture);
        graphic.renderRectangle(exitPoint, batch, exitTexture);
        graphic.render(roomGenerator.getEnemies());
        batch.end();

        // Render points (fixed on screen)
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        GlyphLayout layout = new GlyphLayout(font, "Points: " + chatPlayer.getPoints());
        font.draw(batch, layout, hudCamera.viewportWidth - layout.width - 10, hudCamera.viewportHeight - 10);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        wallTexture.dispose();
        roomTexture.dispose();
        doorTexture.dispose();
        itemTexture.dispose();
        font.dispose();
        exitTexture.dispose();
    }
}
