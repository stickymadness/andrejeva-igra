package com.andrej.igra.control;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class WorldController {
    public static WorldController shared;

    Level level;

    private boolean hasStarted;

    public WorldController() {
        shared = this;
        hasStarted = false;
        level = new Level();
    }

    public void update(float deltaTime) {

        level.update(deltaTime);
    }

    public void dispose() {
        shared = null;
    }

    public boolean hasGameStarted() {
        return hasStarted;
    }

    public void start() {
        hasStarted = true;
        level.ball.velocity.set(0, level.ball.terminalVelocity.y);
    }
}
