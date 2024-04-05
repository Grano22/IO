package com.ksabov.cbo;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CBO extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	private Screen playableArea;
	private Screen mainMenu;
	private Screen currentScreen;
	public GameAssetsManager gameAssetsManager;
	public DebugHelper debugHelper ;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		gameAssetsManager = new GameAssetsManager();

		batch = new SpriteBatch();
		font = new BitmapFont();

		debugHelper = new DebugHelper();

		playableArea = new PlayAreaScreen(this);
		mainMenu = new MainMenuScreen(this);
		this.changeScreen(mainMenu);
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	public void changeScreen(Screen screen) {
		if (currentScreen != null) {
			currentScreen.dispose();
		}

		currentScreen = screen;
		setScreen(currentScreen);
	}

	public void switchToPlayableArea() {
		this.changeScreen(playableArea);
	}

	public Batch getBatch() {
		return batch;
	}
}
