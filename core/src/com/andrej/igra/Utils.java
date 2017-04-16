package com.andrej.igra;

import com.andrej.igra.gameobjects.AbstractGameObject;
import com.badlogic.gdx.Gdx;

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
}
