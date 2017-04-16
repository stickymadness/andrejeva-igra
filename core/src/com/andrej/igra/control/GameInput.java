package com.andrej.igra.control;

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
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (!worldController.hasGameStarted()) {
            worldController.start();
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
