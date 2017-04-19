package com.andrej.igra.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractGameObject {

    public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 velocity;
    public Vector2 scale;
    public Vector2 center;

    public float rotation;
    protected Rectangle bounds;

    protected AbstractGameObject() {
        scale = new Vector2(1, 1);
        dimension = new Vector2(1, 1);

        position = new Vector2();
        origin = new Vector2();
        velocity = new Vector2();
        bounds = new Rectangle();
        center = new Vector2();
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

    protected Vector2 getCenter() {
        center.set(position.x + dimension.x / 2, position.y + dimension.y / 2);
        return center;
    }
}
