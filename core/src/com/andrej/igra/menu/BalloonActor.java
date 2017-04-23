package com.andrej.igra.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class BalloonActor extends Image {

    private static final float CHANGE_DIRECTION_TIME = 2;

    private Vector2 velocity;
    private Vector2 terminalVelocity;
    private float changeDirectionTimer;

    public BalloonActor(Texture region) {
        super(region);

        changeDirectionTimer = 0;
        terminalVelocity = new Vector2(
                MathUtils.random(10, 15) * (MathUtils.randomBoolean() ? -1 : 1),
                MathUtils.random(40, 60)
        );

        velocity = new Vector2(terminalVelocity.x * .1f, terminalVelocity.y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (getY() > Gdx.graphics.getHeight()) {
            setPosition(getX(), -getHeight());
        }

        changeDirectionTimer -= delta;
        if (changeDirectionTimer < 0) {

            if (0.1f < velocity.x && velocity.x > -0.1f) {
                // Slow down
                velocity.x *= 0.9f;
            } else {
                changeDirectionTimer = CHANGE_DIRECTION_TIME;
                terminalVelocity.x *= -MathUtils.random(0.8f, 1.2f);
            }
        } else {
            velocity.x *= 1.2f;
            velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x);
        }

        setPosition(
                getX() + delta * velocity.x,
                getY() + delta * velocity.y
        );
    }
}
