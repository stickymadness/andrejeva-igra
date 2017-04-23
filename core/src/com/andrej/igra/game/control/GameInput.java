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

    public GameInput(WorldController worldController) {
        this.worldController = worldController;
    }

    @Override
    public boolean keyDown(int keycode) {

        if (Input.Keys.LEFT == keycode) {
            worldController.level.playerPad.setDirectionLeft();
        } else if (Input.Keys.RIGHT == keycode) {
            worldController.level.playerPad.setDirectionRight();
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) { return false; }

    @Override
    public boolean keyTyped(char character) { return false; }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (!worldController.hasGameStarted()) {
            worldController.start();
        }

        movePlayerPad(screenX);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
        if (!worldController.hasGameStarted()) {
            return;
        }
        PlayerPad pad = worldController.level.playerPad;
        float padCenter = pad.position.x + pad.dimension.x / 2;
        if (padCenter < screenX / (Gdx.graphics.getWidth() / Constants.GAME_WIDTH)) {
            pad.setDirectionRight();
        } else if (padCenter > screenX / (Gdx.graphics.getWidth() / Constants.GAME_WIDTH)) {
            pad.setDirectionLeft();
        } else {
            pad.stop();
        }
    }
}
