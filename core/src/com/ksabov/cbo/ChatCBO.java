package com.ksabov.cbo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.InputAdapter;

public class ChatCBO extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture wallTexture;
    private Texture roomTexture;
    private ChatRoomGenerator chatRoomGenerator;
    private ChatPlayer chatPlayer;
    private OrthographicCamera camera;

    @Override
    public void create() {
        batch = new SpriteBatch();
        wallTexture = new Texture("wall.jpg");
        roomTexture = new Texture("main_map.png");

        chatRoomGenerator = new ChatRoomGenerator();

        // Example initial player position
        float playerX = chatRoomGenerator.getRooms().get(0).x + chatRoomGenerator.getRooms().get(0).width / 2;
        float playerY = chatRoomGenerator.getRooms().get(0).y + chatRoomGenerator.getRooms().get(0).height / 2;
        chatPlayer = new ChatPlayer(new Texture("hero_standard_pose.png"), playerX, playerY, 50, 50, 200);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(chatPlayer.getPosition().x + chatPlayer.getBounds().width / 2, chatPlayer.getPosition().y + chatPlayer.getBounds().height / 2, 0);
        camera.update();

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

        chatPlayer.update(delta, chatRoomGenerator.getWalls(), null); // Pass walls list to player update

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
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        wallTexture.dispose();
        roomTexture.dispose();

    }
}