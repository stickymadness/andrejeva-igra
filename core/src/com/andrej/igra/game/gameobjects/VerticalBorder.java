package com.andrej.igra.game.gameobjects;

import com.andrej.igra.Constants;
import com.andrej.igra.Utils;
import com.badlogic.gdx.Gdx;
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

public class VerticalBorder extends AbstractGameObject {

    private TextureRegion borderLeft;
    private TextureRegion borderRight;
    private Vector2 rightDimension;
    private Body body;

    public VerticalBorder() {
        float ratio = Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
        float gameHeight = Constants.GAME_WIDTH * ratio;

        borderLeft = new TextureRegion(new Texture("border.png"));
        borderRight = new TextureRegion(new Texture("border_top.png"));

        dimension.set(2.5f, gameHeight);
        rightDimension = new Vector2(dimension.x * .7f, dimension.y);
        dimension.y -= 2.5f * .7f;
        origin.set(dimension.x / 2, dimension.y / 2);

        Utils.setLinearFilter(borderLeft.getTexture());
        Utils.setLinearFilter(borderRight.getTexture());
    }

    @Override
    public void render(SpriteBatch batch) {
        // Reposition body
        if (body != null) {
            body.setTransform(getCenter(), rotation);
        }

        batch.draw(borderLeft, position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation);
        batch.draw(borderRight, position.x, position.y, origin.x, origin.y, rightDimension.x, rightDimension.y, scale.x, scale.y, rotation);
    }

    public void initBody(World world) {
        if (body != null) {
            return;
        }

        Vector2 center = new Vector2();
        center.set(0, 0);

        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(
                dimension.x * .5f,
                dimension.y * .6f,
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
