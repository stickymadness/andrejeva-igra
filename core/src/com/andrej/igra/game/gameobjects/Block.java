package com.andrej.igra.game.gameobjects;

import com.andrej.igra.Utils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class Block extends AbstractGameObject {

    public static final float defaultHeight = 2f;
    public static final float defaultWidth = 5f;

    private ArrayList<TextureRegion> sprites;
    private int value;
    private int durability;
    public Body body;

    public Block(int durability) {
        this.durability = durability;
        this.value = durability;
        this.sprites = new ArrayList<TextureRegion>();

        sprites = getSpritesBasedOnDurability();
        dimension.set(defaultWidth, defaultHeight);

        Utils.setLinearFilter(sprites);
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
        boxBodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(boxBodyDef);
        body.createFixture(polyShape, 1);
        body.setUserData(this);
        body.getPosition().set(position);

        polyShape.dispose();
    }

    @Override
    public void render(SpriteBatch batch) {
        if (durability - 1 >= 0) {
            batch.draw(sprites.get(durability - 1), position.x, position.y, origin.x, origin.y,
                    dimension.x, dimension.y, scale.x, scale.y, rotation);
        }
    }

    private ArrayList<TextureRegion> getSpritesBasedOnDurability() {
        ArrayList<TextureRegion> list = new ArrayList<TextureRegion>();

        // Order of added images is important, first one is the last one that will appear,
        // and last one is the first one that will appear as full block.
        switch (value) {
            case 2:
                list.add(new TextureRegion(new Texture("blocks/block1_2.png")));
                list.add(new TextureRegion(new Texture("blocks/block1_1.png")));
                return list;
            case 3:
                list.add(new TextureRegion(new Texture("blocks/block2_3.png")));
                list.add(new TextureRegion(new Texture("blocks/block2_2.png")));
                list.add(new TextureRegion(new Texture("blocks/block2_1.png")));
                return list;
            default:
                list.add(new TextureRegion(new Texture("blocks/block0.png")));
                return list;
        }
    }

    public void hit() {
        durability -= 1;
    }

    public boolean isDestroyed() {
        return durability == 0;
    }

    public int getValue() {
        return value;
    }

    public void reset() {
        durability = value;
    }
}