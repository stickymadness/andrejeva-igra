package com.andrej.igra.game.control;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class WorldController {
    public static WorldController shared;

    public Level level;
    public World box2dWorld;

    public float time;
    public int score;

    private boolean hasStarted;
    private boolean isRunning;

    public WorldController() {
        initWorld();

        isRunning = true;
        shared = this;
        hasStarted = false;
        level = new Level(box2dWorld);
    }

    public void update(float deltaTime) {

        if (level.isGameOver()) {
            restart();
        } else if (level.isGameComplete()) {
            nextLevel();
        }

        if (isRunning) {
            if (hasStarted) {
                time += deltaTime;
            } else {
                level.respawnBall();
            }
            level.update(deltaTime);
        }
    }

    public void dispose() {
        shared = null;

        if (level != null) {
            level.dispose();
            level = null;
        }
    }

    boolean hasGameStarted() {
        return hasStarted;
    }

    void start() {
        hasStarted = true;
        level.ball.velocity.set(0, level.ball.terminalVelocity.y);
    }

    private void restart() {
        score = 0;
        time = 0;
        hasStarted = false;
        level.restart();
    }

    private void nextLevel() {
        initWorld();

        level = new Level(box2dWorld);
        isRunning = true;
        hasStarted = false;
    }

    private void initWorld() {
        if (box2dWorld != null) {
            box2dWorld.dispose();
            box2dWorld = null;
        }

        box2dWorld = new World(new Vector2(), true);
    }

    public void pause() {
        isRunning = false;
    }

    public void resume() {
        isRunning = true;
    }
}
