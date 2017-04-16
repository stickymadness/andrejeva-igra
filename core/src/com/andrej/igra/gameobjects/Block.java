package com.andrej.igra.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class Block extends AbstractGameObject {

    public static final float defaultHeight = 2f;
    public static final float defaultWidth = 5f;

    private Texture sprite;

    public Block() {
        sprite = new Texture("0_0.png");
        dimension.set(defaultWidth, defaultHeight);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, position.x, position.y, dimension.x, dimension.y);
    }
}
