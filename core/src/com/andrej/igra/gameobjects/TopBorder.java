package com.andrej.igra.gameobjects;

import com.andrej.igra.Constants;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class TopBorder extends AbstractGameObject {

    private TextureRegion border;
    private TextureRegion topBorder;
    private Vector2 topDimension;
    private float topOffset;

    public TopBorder() {
        border = new TextureRegion(new Texture("border.png"));
        topBorder = new TextureRegion(new Texture("border_top.png"));

        dimension.set(Constants.GAME_WIDTH, 2.5f);
        topDimension = new Vector2(dimension.x, dimension.y * 0.7f);
        topOffset = dimension.y * .3f;

        origin.set(dimension.x / 2, dimension.y / 2);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(border, position.x, position.y, dimension.x, dimension.y);
        batch.draw(topBorder, position.x, position.y + topOffset, topDimension.x, topDimension.y);
    }
}
