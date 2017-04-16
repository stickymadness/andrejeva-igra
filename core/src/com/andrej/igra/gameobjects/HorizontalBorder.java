package com.andrej.igra.gameobjects;

import com.andrej.igra.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class HorizontalBorder extends AbstractGameObject {

    private TextureRegion borderLeft;
    private TextureRegion borderRight;
    private Vector2 rightDimension;

    public Vector2 scale;

    public HorizontalBorder() {
        float ratio = Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
        float gameHeight = Constants.GAME_WIDTH * ratio;

        borderLeft = new TextureRegion(new Texture("border.png"));
        borderRight = new TextureRegion(new Texture("border_top.png"));

        dimension.set(2.5f, gameHeight);
        rightDimension = new Vector2(dimension.x * .7f, dimension.y);
        dimension.y -= 2.5f * .7f;

        origin.set(dimension.x / 2, dimension.y / 2);
        scale = new Vector2(1, 1);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(borderLeft, position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation);
        batch.draw(borderRight, position.x, position.y, origin.x, origin.y, rightDimension.x, rightDimension.y, scale.x, scale.y, rotation);
    }
}
