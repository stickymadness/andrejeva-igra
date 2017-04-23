package com.andrej.igra.splash;

import com.andrej.igra.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class SplashStage extends Stage {

    private Texture backgroundTexture;
    private Texture titleTexture;

    public SplashStage() {
        loadTextures();

        buildBackground();
        buildWelcomeTitle();
    }

    private void loadTextures() {
        backgroundTexture = new Texture("menu/background.png");
        titleTexture = new Texture("menu/happyBirthdayTitle.png");

        Utils.setLinearFilter(backgroundTexture);
        Utils.setLinearFilter(titleTexture);
    }

    private void buildBackground() {
        Image background = new Image(backgroundTexture);
        addActor(background);

        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void buildWelcomeTitle() {
        Image title = new Image(titleTexture);
        addActor(title);

        float width = getWidth() * 0.80f;
        float height = width * (title.getHeight() / title.getWidth());
        float padding = (getWidth() - width) / 2;

        title.setSize(width, height);
        title.setPosition(padding, getHeight() / 2 - height / 2);
    }
}