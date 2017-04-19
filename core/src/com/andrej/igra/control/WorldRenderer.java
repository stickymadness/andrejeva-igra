package com.andrej.igra.control;

import com.andrej.igra.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class WorldRenderer {

    private SpriteBatch batch;
    private WorldController worldController;
    private OrthographicCamera camera;
    private Box2DDebugRenderer b2debugRenderer;

    public WorldRenderer(WorldController worldController) {
        batch = new SpriteBatch();
        this.worldController = worldController;

        setupCamera();
        batch.setProjectionMatrix(camera.combined);
        b2debugRenderer = new Box2DDebugRenderer();
    }

    private void setupCamera() {
        float width = Constants.GAME_WIDTH;
        float height = width * (Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());

        camera = new OrthographicCamera(width, height);
        camera.translate(width / 2, height / 2);
        camera.update();
    }

    public void render() {
        batch.begin();

        worldController.level.render(batch);
        if (Constants.BOX_2D_DEBUG_ENABLED) {
            b2debugRenderer.render(worldController.box2dWorld, batch.getProjectionMatrix());
        }

        batch.end();
    }
}
