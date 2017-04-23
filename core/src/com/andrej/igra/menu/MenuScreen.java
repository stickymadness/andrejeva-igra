package com.andrej.igra.menu;

import com.andrej.igra.AndrejGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class MenuScreen extends ScreenAdapter {

    private MenuStage menuStage;

    public MenuScreen(AndrejGame game) {
        AssetsMenu.shared.load();
        menuStage = new MenuStage(game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(menuStage);
    }

    @Override
    public void render(float delta) {

        if (menuStage != null) {
            menuStage.act();
            menuStage.draw();
        }
    }

    @Override
    public void dispose() {
        if (menuStage != null) {
            menuStage.dispose();
            menuStage = null;
        }

        AssetsMenu.shared.dispose();
    }
}
