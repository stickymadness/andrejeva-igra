package com.andrej.igra.game.gameobjects;

import com.andrej.igra.Constants;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class TopBorder extends AbstractGameObject {

    private TextureRegion darkBorder;
    private TextureRegion lightBorder;
    private Vector2 topDimension;
    private float topOffset;
    private Body body;

    public TopBorder() {
        darkBorder = new TextureRegion(new Texture("border.png"));
        lightBorder = new TextureRegion(new Texture("border_top.png"));

        dimension.set(Constants.GAME_WIDTH, 2.5f);
        topDimension = new Vector2(dimension.x, dimension.y * 0.7f);
        topOffset = dimension.y * .3f;

        origin.set(dimension.x / 2, dimension.y / 2);
    }

    @Override
    public void render(SpriteBatch batch) {
        // Reposition body
        if (body != null) {
            body.setTransform(getCenter(), rotation);
        }

        batch.draw(darkBorder, position.x, position.y, dimension.x, dimension.y);
        batch.draw(lightBorder, position.x, position.y + topOffset, topDimension.x, topDimension.y);
    }

    public void initBody(World world) {
        if (body != null) {
            return;
        }

        Vector2 center = new Vector2();
        center.set(0, 0);

        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(
                dimension.x * .6f,
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
}
