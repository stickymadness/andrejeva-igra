package com.andrej.igra.menu;

import com.andrej.igra.Utils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class AssetsMenu {
    public static final AssetsMenu shared = new AssetsMenu();

    public Texture background;
    public Texture title;
    public TextureRegionDrawable btnPlayUp;
    public TextureRegionDrawable btnPlayDown;
    public ArrayList<Texture> balloons;

    private AssetsMenu() {}

    public void load() {
        background = new Texture("menu/background.png");
        title = new Texture("menu/title.png");
        btnPlayUp = new TextureRegionDrawable(new TextureRegion(new Texture("menu/btnPlay.png")));
        btnPlayDown = new TextureRegionDrawable(new TextureRegion(new Texture("menu/btnPlayPressed.png")));

        balloons = new ArrayList<Texture>();
        balloons.add(new Texture("menu/balloon1.png"));
        balloons.add(new Texture("menu/balloon2.png"));
        balloons.add(new Texture("menu/balloon3.png"));
        balloons.add(new Texture("menu/balloon4.png"));

        setTextureFilters();
    }

    Texture getRandomBalloon() {
        return balloons.get(MathUtils.random(0, 3));
    }

    private void setTextureFilters() {
        Utils.setLinearFilter(btnPlayDown.getRegion());
        Utils.setLinearFilter(btnPlayUp.getRegion());
        Utils.setLinearFilter(background);
        Utils.setLinearFilter(title);

        for (Texture text: balloons) {
            Utils.setLinearFilter(text);
        }
    }

    public void dispose() {

        if (background != null) {
            background.dispose();
            background = null;
        }

        if (title != null) {
            title.dispose();
            title = null;
        }

        if (btnPlayDown != null) {
            btnPlayDown.getRegion().getTexture().dispose();
            btnPlayDown = null;
        }

        if (btnPlayUp != null) {
            btnPlayUp.getRegion().getTexture().dispose();
            btnPlayUp = null;
        }

        if (balloons != null && balloons.size() > 0) {
            for (Texture balloon : balloons) {
                balloon.dispose();
            }
            balloons = null;
        }
    }
}
