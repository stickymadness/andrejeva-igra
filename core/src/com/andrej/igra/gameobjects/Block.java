package com.andrej.igra.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class Block extends AbstractGameObject {

    public static final float defaultHeight = 2f;
    public static final float defaultWidth = 5f;

    private Texture sprite;
    private Body body;

    public Block() {
        sprite = new Texture("0_0.png");
        dimension.set(defaultWidth, defaultHeight);
    }

    public void initBody(World world) {
        if (body != null) {
            return;
        }

        Vector2 center = new Vector2();
        center.set(position.x + dimension.x / 2, position.y + dimension.y / 2);

        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(
                dimension.x * .45f,
                dimension.y * .45f,
                center,
                rotation * MathUtils.degRad
        );

        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(boxBodyDef);
        body.createFixture(polyShape, 1);
        body.setUserData(this);
        body.getPosition().set(position);

        polyShape.dispose();
    }


    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, position.x, position.y, dimension.x, dimension.y);
    }
}
