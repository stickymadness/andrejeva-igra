package com.andrej.igra.control;


/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class WorldController {
    public static WorldController shared;

    Level level;

    public WorldController() {
        shared = this;
        level = new Level();
    }

    public void update(float deltaTime) {
        level.update(deltaTime);
    }

    public void dispose() {
        shared = null;
    }
}
