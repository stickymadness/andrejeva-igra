package com.andrej.igra;

import com.andrej.igra.menu.MenuScreen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

public class AndrejGame extends ApplicationAdapter {

	private final int MAX_FPS = 60;
	private final float maxDeltaTime = 1.0f / MAX_FPS;

//	Assets assets;
	ScreenAdapter currentScreen;

	@Override
	public void create () {
//		assets = Assets.shared;
		currentScreen = new MenuScreen(this);
		currentScreen.show();
	}

	@Override
	public void render () {
		if (currentScreen != null) {
			float deltaTime = Math.min(Gdx.graphics.getDeltaTime(), maxDeltaTime);
			currentScreen.render(deltaTime);
		}
	}

	@Override
	public void dispose () {
		if (currentScreen != null) {
			currentScreen.dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
		if (currentScreen != null) {
			currentScreen.resize(width, height);
		}
	}

	@Override
	public void pause() {
		if (currentScreen != null) {
			currentScreen.pause();
		}
	}

	@Override
	public void resume() {
		if (currentScreen != null) {
			currentScreen.resume();
		}
	}

	public void setScreen(ScreenAdapter nextScreen) {
		currentScreen.hide();
		currentScreen.dispose();
		currentScreen = nextScreen;
		currentScreen.show();
	}
}
