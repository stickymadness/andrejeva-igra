package com.andrej.igra.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class Ball extends AbstractGameObject {

    public Vector2 terminalVelocity;

    private TextureRegion sprite;
    private Body body;

    public Ball() {
        sprite = new TextureRegion(new Texture("ball.png"));
        terminalVelocity = new Vector2(18f, 18f);
        dimension.set(3f, 3f);
    }

    public void initBody(World world) {
        if (body != null) {
            return;
        }

        Vector2 center = new Vector2();
        center.set(position.x + dimension.x / 2, position.y + dimension.y / 2);

        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(
                dimension.x * 0.45f,
                dimension.y * 0.45f,
                center,
                rotation * MathUtils.degRad
        );
//        polyShape.setRadius(dimension.x * 0.45f / 2);

        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(boxBodyDef);
        body.createFixture(polyShape, 1);
        body.setUserData(this);

        polyShape.dispose();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, position.x, position.y, dimension.x, dimension.y);
    }

    public void bounceBackFrom(AbstractGameObject block) {
        velocity.set(velocity.x * -1, velocity.y * -1);
    }

    @Override
    public void update(float delta) {
        if (body == null) {
            super.update(delta);
        } else {
            position.set(body.getPosition());
            rotation = body.getAngle() * MathUtils.radiansToDegrees;
        }
    }

    public boolean isOffScreen() {
        return position.y + dimension.y < 0;
    }
}
