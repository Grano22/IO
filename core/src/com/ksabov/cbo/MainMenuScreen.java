package com.ksabov.cbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class MainMenuScreen extends BaseScreen {
    final private OrthographicCamera guiCam;
    final private Stage stage;
    final private Table mainTable;
    //Group group;

    public MainMenuScreen(CBO gameCore) {
        super(gameCore, gameCore.gameAssetsManager);

        stage = new Stage();
        mainTable = createTable();
        handleStartGameButton();
//
//        group = new Group();

        guiCam = new OrthographicCamera(320, 480);
        guiCam.position.set(320f / 2f, 480f / 2f, 0);
//        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
//        textButtonStyle.font = font;
//        TextButton gameButton = new TextButton("New game", textButtonStyle);
        //stage.addActor(gameButton);

        //atlas = new TextureAtlas("skin.atlas");
        //skin = new Skin(Gdx.files.internal("skin.json"), atlas);
    }

    @Override
    public void show() {
    	Gdx.input.setInputProcessor(stage);

        //mainTable.setFillParent(true);
        //mainTable.top();

        //Texture tex = new Texture(Gdx.files.internal("bucket.png"));

//        String name = "";
//        Texture normalTexture = new Texture(Gdx.files.internal(name + "droplet.png"));
//        Texture pressedTexture = new Texture(Gdx.files.internal(name + "bucket.png"));
//        Drawable normalDrawable = new TextureRegionDrawable(normalTexture);
//        Drawable pressedDrawable = new TextureRegionDrawable(pressedTexture);

//        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
//        style.up = normalDrawable;
//        style.down = pressedDrawable;
//        BitmapFont font = new BitmapFont();
//        font.setColor(0.5f,0.4f,0,1);
//        style.font = font;

        //TextButton playButton = new TextButton("Play", GameAssetsManager.DEFAULT_UI_SKIN);
//        TextButton playButton = createTextButton("Play", 10, 20, mainTable);
//        //playButton.getLabel().setColor(new Color(79 / 255.f, 79 / 255.f, 117 / 255.f, 1));
//        TextButton optionsButton = createTextButton("Options", 10, 70, mainTable);
//        TextButton exitButton = createTextButton("Exit", 10, 150, mainTable);

//        playButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                //((Game)Gdx.app.getApplicationListener())
//                gameCore.changeScreen(new PlayAreaScreen(gameCore));
//            }
//        });
//        exitButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                Gdx.app.exit();
//            }
//        });

        //mainTable.add(playButton).width(200).height(300).padLeft(10);
        //mainTable.row();
//        mainTable.add(optionsButton);
//        mainTable.row();
//        mainTable.add(exitButton);

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
                gameCore.changeScreen(new PlayAreaScreen(gameCore));
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        guiCam.update();
        gameCore.getBatch().setProjectionMatrix(guiCam.combined);

        stage.draw();

//        gameCore.getBatch().begin();
//        gameCore.getBatch().draw(null, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        gameCore.getBatch().end();

        show();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
