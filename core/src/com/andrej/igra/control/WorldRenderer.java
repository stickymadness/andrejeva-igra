package com.andrej.igra.control;

import com.andrej.igra.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class WorldRenderer {

    private SpriteBatch batch;
    private WorldController worldController;
    private OrthographicCamera camera;

    public WorldRenderer(WorldController worldController) {
        batch = new SpriteBatch();
        this.worldController = worldController;

        setupCamera();
        batch.setProjectionMatrix(camera.combined);
    }

    private void setupCamera() {
        float width = Constants.GAME_WIDTH;
        float height = width * (Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());
//        float height = Constants.GAME_HEIGHT;

        camera = new OrthographicCamera(width, height);
        camera.translate(width / 2, height / 2);
        camera.update();
    }

    public void render() {

        batch.begin();

        worldController.level.render(batch);

        batch.end();
    }
}
