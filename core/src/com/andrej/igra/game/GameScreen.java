package com.andrej.igra.game;

import com.andrej.igra.game.control.WorldListener;
import com.andrej.igra.game.control.GameInput;
import com.andrej.igra.game.control.WorldController;
import com.andrej.igra.game.control.WorldRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class GameScreen extends ScreenAdapter {

    private WorldController worldController;
    private WorldRenderer worldRenderer;
    private GameInput gameInput;
    private WorldListener worldListener;

    @Override
    public void show() {
        worldController = new WorldController();
        worldRenderer = new WorldRenderer(worldController);
        worldListener = new WorldListener(worldController);
        worldController.box2dWorld.setContactListener(worldListener);

        gameInput = new GameInput(worldController);
        Gdx.input.setInputProcessor(gameInput);
    }

    @Override
    public void render(float delta) {

        if (worldController != null && worldRenderer != null) {
            Gdx.gl.glClearColor(0, 16 / 255f, 31 / 255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            worldController.update(delta);
            worldListener.checkBorderContact();
            worldRenderer.render();
        }
    }

    @Override
    public void dispose() {
        worldController.dispose();
//        Assets.shared.dispose();
    }

    @Override
    public void pause() {
        worldController.pause();
    }

    @Override
    public void resume() {
        worldController.resume();
    }
}
