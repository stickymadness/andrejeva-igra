package com.andrej.igra.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
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

    public Vector2 terminalVelocity;
    public Vector2 bodyPosition;
    public Body body;

    private TextureRegion sprite;
    private float bounceDelay = 0;

    public Ball() {
        sprite = new TextureRegion(new Texture("ball.png"));
        terminalVelocity = new Vector2(20f, 20f);
        dimension.set(3f, 3f);
        bodyPosition = new Vector2();
    }

    public void initBody(World world) {
        if (body != null) {
            return;
        }

        bodyPosition.set(position.x, position.y + dimension.y / 2);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
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
        batch.draw(sprite, position.x, position.y, dimension.x, dimension.y);
    }

    @Override
    public void update(float delta) {
        velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x);
        velocity.y = MathUtils.clamp(velocity.y, -terminalVelocity.y, terminalVelocity.y);

        super.update(delta);

        if (bounceDelay >= 0) {
            bounceDelay -= delta;
        }

        if (body != null) {
            setBodyPosition();
            body.setTransform(bodyPosition.x, bodyPosition.y, rotation);
        }
    }

    private void setBodyPosition() {
        bodyPosition.set(position.x + dimension.x / 2, position.y + dimension.y / 2);
    }

    public boolean isOffScreen() {
        return position.y + dimension.y < 0;
    }

    private boolean canBounce() {
        return bounceDelay < 0;
    }

    public void bounceHorizontal() {
        velocity.x *= -1;
    }

    public void bounceFrom(Block block) {

        if (canBounce()) {
            // TODO: Bounce from block, depending on which side you are you need to send it in according direction
        }
    }

    public void bounceVertical() {
        velocity.y *= -1;
    }

    public void bounceFrom(PlayerPad pad) {
        velocity.set(
                pad.velocity.x / 2 + velocity.x / 2,
                velocity.y * -1
        );
    }
}
