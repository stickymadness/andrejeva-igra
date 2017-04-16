package com.andrej.igra.control;

import com.andrej.igra.Utils;
import com.andrej.igra.gameobjects.Ball;
import com.andrej.igra.gameobjects.Block;
import com.andrej.igra.gameobjects.PlayerPad;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class CollisionHandler implements WorldListener {
    private static final String TAG = WorldListener.class.getSimpleName();

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
        Ball ball = worldController.level.ball;

        ArrayList<Integer> destroy = new ArrayList<Integer>();
        ArrayList<Block> destroyedBlocks = new ArrayList<Block>();

        for (int i = 0; i < worldController.level.blocks.size(); i++) {
            Block block = worldController.level.blocks.get(i);
            if (ball.getBounds().overlaps(block.getBounds())) {
                destroy.add(i);
                destroyedBlocks.add(block);
            }
        }

        if (destroy.size() > 0) {
            worldController.level.blocks.remove(destroyedBlocks.get(0));
            ball.bounceBackFrom(destroyedBlocks.get(0));
            return;
        }

        PlayerPad pad = worldController.level.playerPad;
        if (Utils.checkCollision(ball, pad)) {
            ball.bounceBackFrom(pad);
        }
    }
}
