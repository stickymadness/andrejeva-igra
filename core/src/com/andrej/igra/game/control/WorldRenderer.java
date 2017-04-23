package com.andrej.igra.game.control;

import com.andrej.igra.Constants;
import com.andrej.igra.game.GameStage;
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
    private GameStage stage;
    private Box2DDebugRenderer b2debugRenderer;

    public WorldRenderer(WorldController worldController) {
        batch = new SpriteBatch();
        stage = new GameStage();
        this.worldController = worldController;

        setupCamera();
        batch.setProjectionMatrix(camera.combined);
        b2debugRenderer = new Box2DDebugRenderer();
    }

    private void setupCamera() {
        float width = Constants.GAME_WIDTH;
        float height = width * (Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()) * 1.1f;

        camera = new OrthographicCamera(width, height);
        camera.translate(width / 2, height / 2);
        camera.update();
    }

    public void render() {
        batch.begin();

        worldController.level.render(batch);
        stage.updateScore(worldController.score);
        stage.updateTime((int)worldController.time);

        if (Constants.BOX_2D_DEBUG_ENABLED) {
            b2debugRenderer.render(worldController.box2dWorld, batch.getProjectionMatrix());
        }
        batch.end();

        stage.act();
        stage.draw();
    }
}
