package com.andrej.igra;

import com.badlogic.gdx.Gdx;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class Utils {

    public static float getGameHeight() {
        float ratio = Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
        return Constants.GAME_WIDTH * ratio;
    }
}
