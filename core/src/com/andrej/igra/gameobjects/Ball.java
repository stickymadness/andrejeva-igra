package com.andrej.igra.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class Ball extends AbstractGameObject {

    TextureRegion sprite;

    public Ball() {
        sprite = new TextureRegion(new Texture("ball.png"));
        dimension.set(3f, 3f);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, position.x, position.y, dimension.x, dimension.y);
    }
}
