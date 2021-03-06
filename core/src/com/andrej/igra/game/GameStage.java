package com.andrej.igra.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class GameStage extends Stage {

    private Label scoreLabel;
    private Label timeLabel;
    private Button pauseButton;

    private float padding;
    private float height;
    private float y;

    public GameStage() {
        padding = Gdx.graphics.getWidth() * 0.04f;
        height = Gdx.graphics.getWidth() * 0.1f;
        y = Gdx.graphics.getHeight() - height;

        build();
    }

    private void build() {
        buildScoreLabel();
        buildTimeLabel();
    }

    private void buildScoreLabel() {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont();
        scoreLabel = new Label("SCORE: 0", style);
        addActor(scoreLabel);

        repositionScoreLabel();
    }

    private void buildTimeLabel() {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getFont();
        timeLabel = new Label("0:00", style);
        addActor(timeLabel);

        timeLabel.setPosition(padding, y + height / 2 - timeLabel .getHeight());
    }

    public void updateTime(int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        timeLabel.setText(minutes + ":" + secondsString(seconds));
    }

    public void updateScore(int score) {
        scoreLabel.setText("SCORE: " + score);
        repositionScoreLabel();
    }

    private void repositionScoreLabel() {
        scoreLabel.setPosition(
                getWidth() - padding - scoreLabel.getWidth(),
                y + height / 2 - scoreLabel.getHeight()
        );
    }

    private String secondsString(int seconds) {
        return seconds < 10 ? "0" + seconds : String.valueOf(seconds);
    }

    private BitmapFont getFont() {
        // TODO: Get custom font with calculated height
        BitmapFont font = new BitmapFont();
        float size = 2.5f;
        font.getData().setScale(size, size);
        return font;
    }
}
