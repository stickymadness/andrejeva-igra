package com.andrej.igra.game.gameobjects;

import com.andrej.igra.Constants;
import com.andrej.igra.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class Background extends AbstractGameObject {

    private Texture sprite;

    public Background() {
        float width = Constants.GAME_WIDTH;
        float height = width * (Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()) * 1.1f;

        dimension.set(width, height);
        sprite = new Texture("menu/background.png");

        Utils.setLinearFilter(sprite);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, 0, 0, dimension.x, dimension.y);
    }

    @Override
    public void dispose() {

        if (sprite != null) {
            sprite.dispose();
            sprite = null;
        }
    }
}
