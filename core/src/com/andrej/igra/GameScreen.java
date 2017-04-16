package com.andrej.igra;

import com.andrej.igra.control.CollisionHandler;
import com.andrej.igra.control.GameInput;
import com.andrej.igra.control.WorldController;
import com.andrej.igra.control.WorldListener;
import com.andrej.igra.control.WorldRenderer;
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
        gameInput = new GameInput(worldController);
        worldListener = new CollisionHandler(worldController);

        Gdx.input.setInputProcessor(gameInput);
    }

    @Override
    public void render(float delta) {

        if (worldController != null && worldRenderer != null) {
            Gdx.gl.glClearColor(0, 16 / 255f, 31 / 255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            worldController.update(delta);
            worldListener.playerCollision();
            worldListener.ballCollision();
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
        // TODO: Pause game
    }

    @Override
    public void resume() {
        // TODO: Resume game
    }
}
