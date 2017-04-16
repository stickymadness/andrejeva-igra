package com.andrej.igra.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class Ball extends AbstractGameObject {

    public Vector2 terminalVelocity;

    private TextureRegion sprite;

    public Ball() {
        sprite = new TextureRegion(new Texture("ball.png"));
        terminalVelocity = new Vector2(18f, 18f);
        dimension.set(3f, 3f);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, position.x, position.y, dimension.x, dimension.y);
    }

    public void bounceBackFrom(AbstractGameObject block) {
        velocity.set(velocity.x * -1, velocity.y * -1);
    }
}
