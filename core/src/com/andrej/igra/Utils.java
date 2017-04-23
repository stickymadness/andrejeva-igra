package com.andrej.igra;

import com.andrej.igra.game.gameobjects.AbstractGameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class Utils {

    public static float getGameHeight() {
        float ratio = Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
        return Constants.GAME_WIDTH * ratio;
    }

    public static boolean checkCollision(AbstractGameObject obj1, AbstractGameObject obj2) {
        return obj1.getBounds().overlaps(obj2.getBounds());
    }

    public static void setLinearFilter(TextureRegion reg) {
        setLinearFilter(reg.getTexture());
    }

    public static void setLinearFilter(Texture tex) {
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }
}
