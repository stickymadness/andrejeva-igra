package com.andrej.igra.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractGameObject {

    public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 velocity;

    public float rotation;
    protected Rectangle bounds;

    protected AbstractGameObject() {
        position = new Vector2();
        dimension = new Vector2(1, 1);
        origin = new Vector2();
        velocity = new Vector2();
        bounds = new Rectangle();
        rotation = 0;
    }

    public void update(float delta) {
        updateMotionX(delta);
        updateMotionY(delta);
    }

    public abstract void render(SpriteBatch batch);

    public Rectangle getBounds() {
        bounds.set(position.x, position.y, dimension.x, dimension.y);
        return bounds;
    }

    private void updateMotionX(float delta) {
        position.x += velocity.x * delta;
    }

    private void updateMotionY(float delta) {
        position.y += velocity.y * delta;
    }
}
