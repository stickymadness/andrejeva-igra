package com.andrej.igra.control;

import com.andrej.igra.gameobjects.PlayerPad;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class CollisionHandler implements WorldListener {

    private WorldController worldController;

    public CollisionHandler(WorldController worldController) {
        this.worldController = worldController;
    }

    @Override
    public void playerCollision() {
        PlayerPad pad = worldController.level.playerPad;

        if (pad.position.x < worldController.level.leftBorder.dimension.x) {
            pad.position.x = worldController.level.leftBorder.dimension.x;
        } else if(pad.position.x + pad.dimension.x > worldController.level.rightBorder.position.x) {
            pad.position.x = worldController.level.rightBorder.position.x - pad.dimension.x;
        }
    }

    @Override
    public void ballCollision() {

    }
}
