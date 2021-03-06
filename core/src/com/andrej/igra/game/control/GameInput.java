package com.andrej.igra.game.control;

import com.andrej.igra.Constants;
import com.andrej.igra.game.gameobjects.PlayerPad;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class GameInput implements InputProcessor {

    private WorldController worldController;
    private int lastPressedKey = -1;
    private float gameRatio;

    public GameInput(WorldController worldController) {
        this.worldController = worldController;
        gameRatio = Constants.GAME_WIDTH / Gdx.graphics.getWidth();
    }

    @Override
    public boolean keyDown(int keycode) {

        if (Input.Keys.LEFT == keycode) {
            lastPressedKey = keycode;
            worldController.level.playerPad.setDirectionLeft();
        } else if (Input.Keys.RIGHT == keycode) {
            lastPressedKey = keycode;
            worldController.level.playerPad.setDirectionRight();
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (lastPressedKey == keycode) {
            if (Input.Keys.LEFT == keycode) {
                worldController.level.playerPad.stop();
            } else if (Input.Keys.RIGHT == keycode) {
                worldController.level.playerPad.stop();
            }
            lastPressedKey = -1;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) { return false; }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        movePlayerPad(screenX);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!worldController.hasGameStarted()) {
            worldController.start();
        }

        worldController.level.playerPad.stop();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        movePlayerPad(screenX);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) { return false; }

    @Override
    public boolean scrolled(int amount) { return false; }

    private void movePlayerPad(int screenX) {
        PlayerPad pad = worldController.level.playerPad;
        float padCenter = pad.position.x + pad.dimension.x / 2;

        if (padCenter < screenX / (Gdx.graphics.getWidth() / Constants.GAME_WIDTH)) {
            pad.setDirectionRight();
            pad.setTargetPosition((int)(screenX * gameRatio));
        } else if (padCenter > screenX / (Gdx.graphics.getWidth() / Constants.GAME_WIDTH)) {
            pad.setDirectionLeft();
            pad.setTargetPosition((int)(screenX * gameRatio));
        } else {
            pad.stop();
        }
    }
}
