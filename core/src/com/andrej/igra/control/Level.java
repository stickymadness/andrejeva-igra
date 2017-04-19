package com.andrej.igra.control;

import com.andrej.igra.Constants;
import com.andrej.igra.Utils;
import com.andrej.igra.gameobjects.Ball;
import com.andrej.igra.gameobjects.Block;
import com.andrej.igra.gameobjects.PlayerPad;
import com.andrej.igra.gameobjects.HorizontalBorder;
import com.andrej.igra.gameobjects.TopBorder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

/**
 * Created by Tomaž Ravljen, Drugi Vid d.o.o.
 */

public class Level {

    private enum BLOCK_TYPE {
        EMPTY(0, 0, 0),
        WALL(255, 87, 0),
        BLOCK(255, 180, 0);

        private int color;

        BLOCK_TYPE(int r, int g, int b) {
            color = r << 24 | g << 16 | b << 8 | 0xff;
        }

        public static BLOCK_TYPE getType(int color) {

            if (color == WALL.color) {
                return WALL;
            } else if (color == BLOCK.color) {
                return BLOCK;
            } else {
                return EMPTY;
            }
        }
    }

    private ArrayList<Block> destroyBlocks;

    public PlayerPad playerPad;
    public Ball ball;
    public ArrayList<Block> blocks;
    public ArrayList<Block> levelBlocks;

    public TopBorder topBorder;
    public HorizontalBorder leftBorder;
    public HorizontalBorder rightBorder;

    private World world;

    public Level(World world) {
        this.blocks = new ArrayList<Block>();
        this.levelBlocks = new ArrayList<Block>();
        this.destroyBlocks = new ArrayList<Block>();
        this.world = world;

        load("level00.png");
        createPlayerPad();
        createBall();
        buildWalls();
    }

    private void createBall() {
        ball = new Ball();
        respawnBall();
        ball.initBody(world);
    }

    private void createPlayerPad() {
        playerPad = new PlayerPad();
        centerPlayerPad();
        playerPad.initBody(world);
    }

    private void buildWalls() {
        topBorder = new TopBorder();
        topBorder.position.set(topBorder.dimension.y / 2,
                Utils.getGameHeight() - topBorder.dimension.y);
        topBorder.initBody(world);

        leftBorder = new HorizontalBorder();
        leftBorder.initBody(world);

        rightBorder = new HorizontalBorder();
        rightBorder.scale.x = -1;
        rightBorder.position.set(Constants.GAME_WIDTH - rightBorder.dimension.x, 0);
        rightBorder.initBody(world);
    }

    public void update(float delta) {
        playerPad.update(delta);
        ball.update(delta);
        world.step(delta, 1, 1);
        sweepTheDead();
    }

    private void sweepTheDead() {
        if (world.isLocked()) {
            return;
        }

        for (Block block: destroyBlocks) {
            world.destroyBody(block.body);
            blocks.remove(block);

            block.body.setUserData(null);
            block.body = null;
        }

        destroyBlocks.clear();
    }

    public void render(SpriteBatch batch) {

        topBorder.render(batch);
        leftBorder.render(batch);
        rightBorder.render(batch);

        for (Block block: blocks) {
            block.render(batch);
        }

        playerPad.render(batch);
        ball.render(batch);
    }

    private void load(String filePath) {
        Pixmap pixmap = new Pixmap(Gdx.files.internal(filePath));

        float ratioX = (Constants.GAME_WIDTH) / pixmap.getWidth();
        float ratioY = (Utils.getGameHeight()) / pixmap.getHeight();

        for (int x = 0; x < pixmap.getWidth(); x++) {
            for (int y = 0; y < pixmap.getHeight(); y++) {
                int color = pixmap.getPixel(x, y);
                BLOCK_TYPE blockType = BLOCK_TYPE.getType(color);
                Block block;

                float invertedY = Utils.getGameHeight() - (y + 2.4f) * 2f;
                switch (blockType) {
                    case BLOCK:
                        block = new Block();
                        block.position.set(x * ratioX, invertedY);
                        block.initBody(world);

                        levelBlocks.add(block);
                        blocks.add(block);
                        break;

                    case WALL:
//                        Gdx.app.error("Level", "WALL");
                        break;

                    case EMPTY:
//                        Gdx.app.error("Level", "EMPTY");
                        break;
                }
            }
        }
    }

    boolean isGameOver() {
        return ball.isOffScreen();
    }

    void restart() {
        centerPlayerPad();
        respawnBall();
        respawnBlocks();
    }

    private void centerPlayerPad() {
        playerPad.position.set(Constants.GAME_WIDTH / 2 - playerPad.dimension.x / 2, 2);
        playerPad.velocity.set(0, 0);
    }

    private void respawnBall() {
        ball.position.set(playerPad.position.x + playerPad.dimension.x / 2,
                playerPad.position.y + playerPad.dimension.y);
        ball.velocity.set(0, 0);
    }

    private void respawnBlocks() {
        blocks.clear();
        blocks.addAll(levelBlocks);

        for (Block block: blocks) {
            block.initBody(world);
        }
    }

    void destroy(Block block) {
        destroyBlocks.add(block);
    }

    public boolean isGameComplete() {
        return blocks.size() == 0;
    }
}
