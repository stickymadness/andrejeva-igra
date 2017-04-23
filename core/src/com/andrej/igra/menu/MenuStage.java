package com.andrej.igra.menu;

import com.andrej.igra.AndrejGame;
import com.andrej.igra.game.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sun.org.apache.bcel.internal.generic.BALOAD;

import java.util.ArrayList;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class MenuStage extends Stage {

    private Image background;
    private Image title;
    private Button playButton;
    private ArrayList<BalloonActor> balloons;

    private AssetsMenu assets;
    private AndrejGame game;

    public MenuStage(AndrejGame game) {
        this.game = game;
        assets = AssetsMenu.shared;
        balloons = new ArrayList<BalloonActor>();

        build();
    }

    private void build() {
        buildBackground();
        buildBalloons();
        buildTitle();
        buildPlayButton();
    }

    private void buildBackground() {
        background = new Image(assets.background);
        addActor(background);

        background.setSize(getWidth(), getHeight());
    }

    private void buildBalloons() {
        for (Texture texture: assets.balloons) {
            buildBalloon(texture);
        }
    }

    private void buildBalloon(Texture texture) {
        BalloonActor balloon = new BalloonActor(texture);
        addActor(balloon);

        float maxSize = Gdx.graphics.getWidth() * 0.22f;
        float scale = MathUtils.random(.75f, 1f);
        float width = scale * maxSize;
        float height = scale * (maxSize * (balloon.getHeight() / balloon.getWidth()));

        balloon.setSize(width, height);
        balloon.setPosition(
                MathUtils.random(-width / 2, Gdx.graphics.getWidth() - width / 2),
                MathUtils.random(-height * 4, 0)
        );
    }

    private void buildTitle() {
        title = new Image(assets.title);
        addActor(title);

        float width = getWidth() * 0.80f;
        float height = width * (title.getHeight() / title.getWidth());
        float padding = (getWidth() - width) / 2;

        title.setSize(width, height);
        title.setPosition(padding, getHeight() / 2 + height / 2 - padding * 2.5f);
    }

    private void buildPlayButton() {
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = assets.btnPlayUp;
        style.down = assets.btnPlayDown;

        playButton = new Button(style);
        addActor(playButton);

        float width = getWidth() * .5f;
        float height = width * (playButton.getHeight() / playButton.getWidth());

        playButton.setSize(width, height);
        playButton.setPosition(
                getWidth() / 2 - width / 2,
                title.getY() - getWidth() * 0.25f - playButton.getHeight()
        );

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen());
            }
        });
    }
}
