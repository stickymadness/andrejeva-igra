package com.andrej.igra.menu;

import com.andrej.igra.Utils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class AssetsMenu {

    public Texture background;
    public Texture title;
    public TextureRegionDrawable btnPlayUp;
    public TextureRegionDrawable btnPlayDown;

    public AssetsMenu() {

        background = new Texture("menu/background.png");
        title = new Texture("menu/title.png");
        btnPlayUp = new TextureRegionDrawable(new TextureRegion(new Texture("menu/btnPlay.png")));
        btnPlayDown = new TextureRegionDrawable(new TextureRegion(new Texture("menu/btnPlayPressed.png")));

        Utils.setLinearFilter(btnPlayDown.getRegion());
        Utils.setLinearFilter(btnPlayUp.getRegion());
        Utils.setLinearFilter(background);
        Utils.setLinearFilter(title);
    }
}
