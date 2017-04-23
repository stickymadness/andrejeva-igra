package com.andrej.igra.splash;

import com.andrej.igra.AndrejGame;
import com.andrej.igra.menu.MenuScreen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class SplashScreen extends ScreenAdapter {

    private static final float SPLASH_SCREEN_TIME = 3f;

    private float showingTime;
    private AndrejGame game;
    private Stage stage;

    public SplashScreen(AndrejGame game) {
        this.game = game;
        stage = new SplashStage();

        showingTime = 0;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        stage.draw();

        showingTime += delta;

        if (isShowTimeOver()) {
            showMainMenu();
        }
    }

    private void showMainMenu() {
        game.setScreen(new MenuScreen(game));
    }

    private boolean isShowTimeOver() {
        return SPLASH_SCREEN_TIME < showingTime;
    }
}
