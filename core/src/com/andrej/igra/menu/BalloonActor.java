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
                MathUtils.random(Gdx.graphics.getWidth() / 32f, Gdx.graphics.getHeight() / 37f) * (MathUtils.randomBoolean() ? -1 : 1),
                MathUtils.random(Gdx.graphics.getWidth() / 8f, Gdx.graphics.getHeight() / 9f)
        );

        velocity = new Vector2(terminalVelocity.x * .1f, terminalVelocity.y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (isOffScreen()) {
            spawnOnBottomOfScreen();
        }

        turnAroundIfGoingOffscreenHorizontally();
        changeDirection(delta);

        updateMotion(delta);
    }

    private void updateMotion(float delta) {
        setPosition(
                getX() + delta * velocity.x,
                getY() + delta * velocity.y
        );
    }

    private void changeDirection(float delta) {
        changeDirectionTimer -= delta;
        if (changeDirectionTimer < 0) {

            if (0.1f < velocity.x && velocity.x > -0.1f) {
                // Slow down
                velocity.x *= 0.9f;
            } else {
                changeDirectionTimer = CHANGE_DIRECTION_TIME;
                terminalVelocity.x *= MathUtils.random(0.8f, 1.2f);
            }
        } else {
            velocity.x *= 1.2f;
            velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x);
        }
    }

    private float getCenterX() {
        return getX() + getWidth() / 2;
    }

    private void invertHorizontalVelocity() {
        velocity.x *= -1;
    }

    private void turnAroundIfGoingOffscreenHorizontally() {
        if (getCenterX() > Gdx.graphics.getWidth()) {
            if (velocity.x > 0) {
                invertHorizontalVelocity();
            }
        } else if (getCenterX() < 0) {
            if (velocity.x < 0) {
                invertHorizontalVelocity();
            }
        }
    }

    private boolean isOffScreen() {
        return getY() > Gdx.graphics.getHeight();
    }

    private void spawnOnBottomOfScreen() {
        setPosition(getX(), -getHeight());
    }
}
