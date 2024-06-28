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

public class ChatCBO extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture wallTexture;
    private Texture roomTexture;
    private Texture doorTexture;
    private Texture itemTexture;
    private BitmapFont font;
    private ChatRoomGenerator chatRoomGenerator;
    private ChatPlayer chatPlayer;
    private OrthographicCamera camera;
    private OrthographicCamera hudCamera; // Separate camera for HUD

    @Override
    public void create() {
        batch = new SpriteBatch();
        wallTexture = new Texture("wall.jpg");
        roomTexture = new Texture("main_map.png");
        doorTexture = new Texture("door.png");
        itemTexture = new Texture("item.png");
        font = new BitmapFont(); // Default font

        chatRoomGenerator = new ChatRoomGenerator();

        // Example initial player position
        float playerX = chatRoomGenerator.getRooms().get(0).x + chatRoomGenerator.getRooms().get(0).width / 2;
        float playerY = chatRoomGenerator.getRooms().get(0).y + chatRoomGenerator.getRooms().get(0).height / 2;
        chatPlayer = new ChatPlayer(new Texture("hero_standard_pose.png"), playerX, playerY, 30, 30, 200); // Reduced player size

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

        chatPlayer.update(delta, chatRoomGenerator.getWalls(), chatRoomGenerator.getDoors(), chatRoomGenerator.getItems(), chatRoomGenerator); // Pass doors and items list to player update

        camera.position.set(chatPlayer.getPosition().x + chatPlayer.getBounds().width / 2, chatPlayer.getPosition().y + chatPlayer.getBounds().height / 2, 0);
        camera.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        // Render walls
        chatRoomGenerator.renderWalls(batch, wallTexture);
        // Render rooms
        chatRoomGenerator.renderRooms(batch, roomTexture);
        // Render player
        chatPlayer.render(batch);
        // Render doors
        chatRoomGenerator.renderDoors(batch, doorTexture);
        // Render items
        chatRoomGenerator.renderItems(batch, itemTexture);
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
    }
}
