package com.andrej.igra.game.gameobjects;

import com.andrej.igra.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class Ball extends AbstractGameObject {
    private static final String TAG = Ball.class.getSimpleName();
    private static final float MAX_BOUNCE_DELAY = .1f;
    private static final float ROTATION_SPEED = 720f;

    public Vector2 terminalVelocity;
    public Vector2 bodyPosition;
    public Body body;

    private TextureRegion sprite;
    private float bounceDelay = 0;

    public Ball() {
        sprite = new TextureRegion(new Texture("ball.png"));
        terminalVelocity = new Vector2(20f, 20f);
        dimension.set(2.5f, 2.5f);
        origin.set(dimension.x / 2, dimension.y / 2);
        bodyPosition = new Vector2();

        Utils.setLinearFilter(sprite.getTexture());
    }

    public void initBody(World world) {
        if (body != null) {
            return;
        }

        bodyPosition.set(position.x, position.y + dimension.y / 2);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(bodyPosition);
        bodyDef.active = true;

        CircleShape circle = new CircleShape();
        circle.setRadius(dimension.x * .45f);
        body = world.createBody(bodyDef);
        body.setUserData(this);

        body.createFixture(circle, 1);

        circle.dispose();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, position.x, position.y, origin.x, origin.y,
                dimension.x, dimension.y, scale.x, scale.y, rotation);
    }

    @Override
    public void update(float delta) {
        velocity.clamp(-terminalVelocity.x, terminalVelocity.x);

        super.update(delta);

        if (bounceDelay >= 0) {
            bounceDelay -= delta;
        }

        if (velocity.x != 0) {
            rotation += delta * ROTATION_SPEED * (velocity.x / terminalVelocity.x);
        }

        if (body != null) {
            setBodyPosition();
            body.setTransform(bodyPosition.x, bodyPosition.y, 0);
        }
    }

    private void setBodyPosition() {
        bodyPosition.set(position.x + dimension.x / 2, position.y + dimension.y / 2);
    }

    public boolean isOffScreen() {
        return position.y + dimension.y < 0;
    }

    public void bounceHorizontal() {
        invertHorizontalVelocity();
    }

    public void bounceFrom(Block block) {
        if (!canBounce()) {
            return;
        }

        float intersectionX = 0;
        float intersectionY = 0;

        if (block.position.x > position.x && block.position.x < position.x + dimension.x) {
            intersectionX = Math.abs(block.position.x - position.x);
        }

        if (block.position.y > position.y && block.position.y < position.y + dimension.y) {
            intersectionY = Math.abs(block.position.y - position.y);
        }

        Gdx.app.error(TAG, "intersectionX: " + intersectionX);
        Gdx.app.error(TAG, "intersectionY: " + intersectionY);

        if (intersectionX < intersectionY) {
            bounceVertical();
        } else {
            bounceHorizontal();
        }

        resetBounce();
    }

    public void bounceVertical() {
        invertVerticalVelocity();
    }

    public void bounceFrom(PlayerPad pad) {

        if (pad.position.y + pad.dimension.y - position.y < 1f) {

            float relativeIntersectX = (pad.position.x + pad.dimension.x / 2) - body.getWorldCenter().x;
            float normalizedRelativeIntersectionX = relativeIntersectX / (pad.dimension.x / 2);
            velocity.x = terminalVelocity.x * normalizedRelativeIntersectionX * -1;
            velocity.y *= -1;
        }
    }

    private void invertVerticalVelocity() {
        velocity.y *= -1;
    }

    private void invertHorizontalVelocity() {
        velocity.x *= -1;
    }

    private void resetBounce() {
        bounceDelay = MAX_BOUNCE_DELAY;
    }

    private boolean canBounce() {
        return bounceDelay < 0;
    }
}
