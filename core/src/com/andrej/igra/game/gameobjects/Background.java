package com.andrej.igra.game.gameobjects;

import com.andrej.igra.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class Background extends AbstractGameObject {

    private Texture sprite;

    public Background() {
        sprite = new Texture("menu/background.png");
        float width = Constants.GAME_WIDTH;
        float height = width * (Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()) * 1.1f;
        dimension.set(width, height);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, 0, 0, dimension.x, dimension.y);
    }
}
