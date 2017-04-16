package com.andrej.igra.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class PlayerPad extends AbstractGameObject {

    private Vector2 terminalVelocity;
    private Texture sprite;

    public PlayerPad() {
        sprite = new Texture("platform.png");
        dimension.set(14f, 3.6f);
        terminalVelocity = new Vector2(12, 0);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, position.x, position.y, dimension.x, dimension.y);
    }

    public void setDirectionLeft() {
        velocity.set(-terminalVelocity.x, 0);
    }

    public void setDirectionRight() {
        velocity.set(terminalVelocity.x, 0);
    }
}
