package com.ksabov.cbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen extends BaseScreen {
    final private OrthographicCamera guiCam;
    final private Stage stage;
    final private Table mainTable;

    public MainMenuScreen(CBO gameCore) {
        super(gameCore, gameCore.gameAssetsManager);

        stage = new Stage();
        mainTable = createTable();
        handleStartGameButton();
        handleAboutButton();
        handleExitButton();

        guiCam = new OrthographicCamera(320, 480);
        guiCam.position.set(320f / 2f, 480f / 2f, 0);
    }

    @Override
    public void show() {
    	Gdx.input.setInputProcessor(stage);
        stage.addActor(mainTable);
    }


    private void handleStartGameButton() {
        createTextButton("New Game", 0, mainTable.getHeight()/10, mainTable);

        Actor newButton = mainTable.getCells().get(0).getActor();
        newButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
//                setScreenWithTransition(
//                        (BaseScreen) gdxGame.getScreen(),
//                        new MenuNewGameScreen(gameCore, (BaseScreen) gdxGame.getScreen(), resourceManager),
//                        new ArrayList<>()
//                );
                gameCore.switchToPlayableArea();
            }
        });
    }

    private void handleAboutButton() {
        createTextButton("About", 0, mainTable.getHeight()/10, mainTable);

        Actor optionButton = mainTable.getCells().get(1).getActor();
        optionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {

            }
        });
    }

    private void handleExitButton() {
        createTextButton("Exit", 0, mainTable.getHeight()/10, mainTable);

        Actor exitButton = mainTable.getCells().get(2).getActor();
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        //Gdx.gl.glClearColor(0, 0, 0, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        guiCam.update();
        gameCore.getBatch().setProjectionMatrix(guiCam.combined);

        stage.draw();

        //gameCore.getBatch().begin();
//        gameCore.getBatch().draw(null, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //gameCore.getBatch().end();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
