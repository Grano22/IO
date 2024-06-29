package com.ksabov.cbo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private Player player;
    private OrthographicCamera camera;
    private Texture exitTexture;
    private Texture enemyTexture;
    private OrthographicCamera hudCamera; // Separate camera for HUD
    private GameAssetsManager gameAssetsManager;
    private final CollisionPositionApplier collisionPositionApplier = new CollisionPositionApplier();
    private Stage stage;

    private Rectangle exitPoint;
    private Skin uiSkin;

    @Override
    public void create() {
        batch = new SpriteBatch();
        stage = new Stage();
        graphic = new GraphicRenderer(batch);
        wallTexture = new Texture("wall.jpg");
        roomTexture = new Texture("main_map.png");
        doorTexture = new Texture("door.png");
        itemTexture = new Texture("item.png");
        exitTexture = new Texture("exit.png");
        font = new BitmapFont(); // Default font
        gameAssetsManager = new GameAssetsManager();

        uiSkin = new Skin();

        Texture buttonUpTexture = new Texture(Gdx.files.internal("ui_btn.png"));
        Texture buttonDownTexture = new Texture(Gdx.files.internal("ui_btn.png"));
        Texture boxBackgroundTexture = new Texture(Gdx.files.internal("menu_box.png"));
        Texture headerTexture = new Texture(Gdx.files.internal("game_title.png"));

        uiSkin.add("buttonUp", buttonUpTexture);
        uiSkin.add("buttonDown", buttonDownTexture);
        uiSkin.add("boxBackground", boxBackgroundTexture);
        uiSkin.add("header", headerTexture);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonUpTexture));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(buttonDownTexture));
        textButtonStyle.font = font;

        uiSkin.add("default", textButtonStyle);

        roomGenerator = new RoomGenerator(gameAssetsManager);
        roomsUtils = new RoomsUtils(roomGenerator.getRooms());
        itemsGenerator = new ItemsGenerator();

        // Example initial player position
        float playerX = roomGenerator.getRooms().get(0).x + roomGenerator.getRooms().get(0).width / 2;
        float playerY = roomGenerator.getRooms().get(0).y + roomGenerator.getRooms().get(0).height / 2;
        player = new Player(new Texture("hero_standard_pose.png"), playerX, playerY, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT, 200); // Reduced player size
        Rectangle randomizedRoom = roomOperator.randomizeRoom(roomGenerator.getRooms());
        player.moveToRoom(randomizedRoom);

        Optional<Rectangle> exitPointResult = itemsGenerator.generateExitPoint(player, roomsUtils);

        if (!exitPointResult.isPresent()) {
            System.out.println("Cannot generate exit point");
            System.exit(1);
        }

        exitPoint = exitPointResult.get();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(player.getPosition().x + player.getBounds().width / 2, player.getPosition().y + player.getBounds().height / 2, 0);
        camera.update();

        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCamera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        hudCamera.update();

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean scrolled(float amountX, float amountY) {
                camera.zoom += amountY * 0.1f;
                return true;
            }
        });
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        List<Rectangle> collidingBounds = Stream.concat(roomGenerator.getWalls().stream(), roomGenerator.getDoors().stream())
                .collect(Collectors.toList());
        player.update(delta, roomGenerator.getWalls(), roomGenerator.getDoors(), roomGenerator.getItems(), roomGenerator); // Pass doors and items list to player update

        camera.position.set(player.getPosition().x + player.getBounds().width / 2, player.getPosition().y + player.getBounds().height / 2, 0);
        camera.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        if (player.isAlive()) {
            if (player.getBounds().overlaps(exitPoint)) {
                roomGenerator = new RoomGenerator(gameAssetsManager);
                player.setPoints(0);
                Rectangle randomizedRoom = roomOperator.randomizeRoom(roomGenerator.getRooms());
                player.moveToRoom(randomizedRoom);
                Rectangle farthestRoom = roomsUtils.getFarthestRoomToThePlayer(player);
                exitPoint.setPosition(farthestRoom.x, farthestRoom.y);
            }

            checkPlayerEnemyCollision();

            for (Enemy enemy : roomGenerator.getEnemies()) {
                enemy.followPlayer(player, collisionPositionApplier.apply(collidingBounds));
            }
        }

        batch.begin();
        // Render walls
        roomGenerator.renderWalls(batch, wallTexture);
        // Render rooms
        roomGenerator.renderRooms(batch, roomTexture);
        // Render player
        player.render(batch);
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
        GlyphLayout layout = new GlyphLayout(font, "Points: " + player.getPoints());
        font.draw(batch, layout, hudCamera.viewportWidth - layout.width - 10, hudCamera.viewportHeight - 10);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    private void checkPlayerEnemyCollision() {
        for (Enemy enemy: roomGenerator.getEnemies()) {
            //enemy.getPosition().epsilonEquals(player.getPosition(), 0.1f)
            if (player.getBounds().overlaps(enemy.getBounds())) {
                player.takeDamage();
                if (!player.isAlive()) {
                    System.out.println("game over!");
                    gameOver();
                }
            }
        }
    }

    private void gameOver() {
        showGameOverDialog();
    }

    private void showGameOverDialog() {
        Table table = new Table();
        table.setFillParent(true);
        //table.setBackground(new TextureRegionDrawable(new TextureRegion(uiSkin.getRegion("boxBackground"))));
//        table.setBackground(new TextureRegionDrawable(new TextureRegion(uiSkin.getRegion("boxBackground").getTexture())) {
//            @Override
//            public void draw(Batch batch, float x, float y, float width, float height) {
//                batch.draw(uiSkin.getRegion("boxBackground").getTexture(), x, y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//            }
//        });
        //table.setBackground(new StretchableDrawable(new TextureRegion(uiSkin.getRegion("boxBackground"))));
//        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(uiSkin.getRegion("boxBackground").getTexture()));
//        backgroundDrawable.setMinWidth(Gdx.graphics.getWidth());
//        backgroundDrawable.setMinHeight(Gdx.graphics.getHeight());

        table.top().left();
        Drawable tableBackground = new Drawable() {
            @Override
            public void draw(Batch batch, float x, float y, float width, float height) {
                batch.draw(uiSkin.getRegion("boxBackground").getTexture(), x, y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            }

            @Override
            public float getLeftWidth() { return 0; }

            @Override
            public void setLeftWidth(float leftWidth) { }

            @Override
            public float getRightWidth() { return 0; }

            @Override
            public void setRightWidth(float rightWidth) { }

            @Override
            public float getTopHeight() { return 0; }

            @Override
            public void setTopHeight(float topHeight) { }

            @Override
            public float getBottomHeight() { return 0; }

            @Override
            public void setBottomHeight(float bottomHeight) { }

            @Override
            public float getMinWidth() { return 0; }

            @Override
            public void setMinWidth(float minWidth) { }

            @Override
            public float getMinHeight() { return 0; }

            @Override
            public void setMinHeight(float minHeight) { }
        };
        table.setBackground(tableBackground);

        stage.addActor(table);

        Image header = new Image(new TextureRegionDrawable(new TextureRegion(uiSkin.getRegion("header"))));
        table.add(header).center().padBottom(20);
        table.row();

        TextButton button = new TextButton("Restart", uiSkin);
        button.setPosition(Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() / 2f - 25); // Center the button
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //restartGame();
                System.out.println("something");
                //stage.getActors().removeRange(0, stage.getActors().size);
                stage.clear();
            }
        });
        //stage.addActor(button);

        //table.add(button).center();
        table.add(button).width(Value.percentWidth(0.8f, table)).height(Value.percentHeight(0.1f, table)).pad(10);

        // Display the dialog
        //table.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        table.setSize(uiSkin.getRegion("boxBackground").getTexture().getWidth(), uiSkin.getRegion("boxBackground").getTexture().getHeight());

        // Center the table on the screen
//        table.setPosition(
//                (Gdx.graphics.getWidth() - table.getWidth()) / 2,
//                (Gdx.graphics.getHeight() - table.getHeight()) / 2
//        );
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
