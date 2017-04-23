package com.andrej.igra.game.control;

import com.andrej.igra.game.gameobjects.Ball;
import com.andrej.igra.game.gameobjects.Block;
import com.andrej.igra.game.gameobjects.PlayerPad;
import com.andrej.igra.game.gameobjects.TopBorder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class WorldListener implements ContactListener {
    private static final String TAG = WorldListener.class.getSimpleName();

    private WorldController worldController;

    public WorldListener(WorldController worldController) {
        this.worldController = worldController;
        worldController.box2dWorld.setContactListener(this);
    }

    public void checkBorderContact() {

        // For ball
        Ball ball = worldController.level.ball;

        if (ball.position.x < worldController.level.leftBorder.dimension.x) {
            ball.bounceHorizontal();
        } else if (ball.position.x + ball.dimension.x > worldController.level.rightBorder.position.x) {
            ball.bounceHorizontal();
        } else if (ball.body.getPosition().y + ball.dimension.y * 0.5f > worldController.level.topBorder.position.y) {
            ball.bounceVertical();
        }

        // For pad
        PlayerPad pad = worldController.level.playerPad;

        if (pad.position.x < worldController.level.leftBorder.dimension.x) {
            pad.position.x = worldController.level.leftBorder.dimension.x;
            pad.stop();
        } else if (pad.position.x + pad.dimension.x > worldController.level.rightBorder.position.x) {
            pad.position.x = worldController.level.rightBorder.position.x - pad.dimension.x;
            pad.stop();
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Object obj1 = contact.getFixtureA().getBody().getUserData();
        Object obj2 = contact.getFixtureB().getBody().getUserData();

        if (obj1 instanceof Ball || obj2 instanceof Ball) {
            Gdx.app.error(TAG, "Contact objects: " + obj1.getClass().getSimpleName() + " - " + obj2.getClass().getSimpleName());

            if (obj2 instanceof Block || obj1 instanceof Block) {
                Block block = (Block)(obj1 instanceof Block ? obj1 : obj2);
                worldController.level.destroy(block);
                worldController.level.ball.bounceFrom(block);
            } else if (obj1 instanceof PlayerPad || obj2 instanceof PlayerPad) {
                worldController.level.ball.bounceFrom(worldController.level.playerPad);
            } else if (obj1 instanceof TopBorder || obj2 instanceof TopBorder) {
                worldController.level.ball.bounceVertical();
            }
//            else if (obj1 instanceof VerticalBorder || obj2 instanceof VerticalBorder) {
//                worldController.level.ball.bounceHorizontal();
//            }
        }
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
