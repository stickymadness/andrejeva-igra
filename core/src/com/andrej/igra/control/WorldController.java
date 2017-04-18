package com.andrej.igra.control;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class WorldController {
    public static WorldController shared;

    Level level;
    World box2dWorld;

    private boolean hasStarted;

    public WorldController() {
        shared = this;
        hasStarted = false;
        box2dWorld = new World(new Vector2(), false);
        level = new Level(box2dWorld);
    }

    public void update(float deltaTime) {

        if (level.isGameOver()) {
            restart();
        }
        level.update(deltaTime);
    }

    public void dispose() {
        shared = null;
    }

    boolean hasGameStarted() {
        return hasStarted;
    }

    void start() {
        hasStarted = true;
        level.ball.velocity.set(0, level.ball.terminalVelocity.y);
    }

    void restart() {
        hasStarted = false;
        level.restart();
    }
}
