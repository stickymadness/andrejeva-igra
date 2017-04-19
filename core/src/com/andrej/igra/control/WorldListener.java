package com.andrej.igra.control;

import com.andrej.igra.Utils;
import com.andrej.igra.gameobjects.Ball;
import com.andrej.igra.gameobjects.Block;
import com.andrej.igra.gameobjects.HorizontalBorder;
import com.andrej.igra.gameobjects.PlayerPad;
import com.andrej.igra.gameobjects.TopBorder;
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

    public void padCollision() {
        PlayerPad pad = worldController.level.playerPad;

        if (pad.position.x < worldController.level.leftBorder.dimension.x) {
            pad.position.x = worldController.level.leftBorder.dimension.x;
        } else if(pad.position.x + pad.dimension.x > worldController.level.rightBorder.position.x) {
            pad.position.x = worldController.level.rightBorder.position.x - pad.dimension.x;
        }
    }

    public void ballCollision() {
        Ball ball = worldController.level.ball;

        PlayerPad pad = worldController.level.playerPad;
        if (Utils.checkCollision(ball, pad)) {
            ball.bounceBack();
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Object obj1 = contact.getFixtureA().getBody().getUserData();
        Object obj2 = contact.getFixtureB().getBody().getUserData();

        if (obj1 instanceof Ball || obj2 instanceof Ball) {
            Gdx.app.error(TAG, "Contact objects: " + obj1.getClass().getSimpleName() + ", " + obj2.getClass().getSimpleName());

            if (obj2 instanceof Block || obj1 instanceof Block) {
                Ball ball = (Ball)(obj1 instanceof Ball ? obj1 : obj2);
                Block block = (Block)(obj1 instanceof Block ? obj1 : obj2);
                worldController.level.destroy(block);
                ball.bounceFrom(block);
                Gdx.app.error(TAG, "bounceBack");

            } else if(obj1 instanceof PlayerPad || obj2 instanceof PlayerPad) {
                Ball ball = (Ball)(obj1 instanceof Ball ? obj1 : obj2);
                PlayerPad pad = (PlayerPad)(obj1 instanceof PlayerPad ? obj1 : obj2);
                ball.velocity.x = pad.velocity.x / 2 + ball.velocity.x;
                ball.bounceBack();

            } else if(obj1 instanceof TopBorder || obj2 instanceof TopBorder) {
                Ball ball = (Ball)(obj1 instanceof Ball ? obj1 : obj2);
                ball.bounceVertical();

            } else if(obj1 instanceof HorizontalBorder || obj2 instanceof HorizontalBorder) {
                Ball ball = (Ball)(obj1 instanceof Ball ? obj1 : obj2);
                ball.bounceHorizontal();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
//        Gdx.app.error(TAG, "endContact");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
//        Gdx.app.error(TAG, "preSolve");
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
//        Gdx.app.error(TAG, "postSolve");
    }
}
