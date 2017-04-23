package com.andrej.igra.game.control;

import com.andrej.igra.Constants;
import com.andrej.igra.Utils;
import com.andrej.igra.game.gameobjects.Background;
import com.andrej.igra.game.gameobjects.Ball;
import com.andrej.igra.game.gameobjects.Block;
import com.andrej.igra.game.gameobjects.PlayerPad;
import com.andrej.igra.game.gameobjects.TopBorder;
import com.andrej.igra.game.gameobjects.VerticalBorder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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

    public Background background;
    public TopBorder topBorder;
    public VerticalBorder leftBorder;
    public VerticalBorder rightBorder;

    private World world;

    public Level(World world) {
        this.background = new Background();
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

        leftBorder = new VerticalBorder();
        leftBorder.initBody(world);

        rightBorder = new VerticalBorder();
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

    public void render(SpriteBatch batch) {

        background.render(batch);
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

        // TODO: Try generating random positions for half of the screen and project horizontally the other half. And remove loading from level file

        float ratioX = (Constants.GAME_WIDTH) / pixmap.getWidth();
//        float ratioY = (Utils.getGameHeight()) / pixmap.getHeight();
        ArrayList<Vector2> possibleBlockList = new ArrayList<Vector2>();
        float width = ratioX;

        for (int x = 0; x < pixmap.getWidth() / 2; x++) {
            for (int y = 0; y < pixmap.getHeight(); y++) {
                int color = pixmap.getPixel(x, y);
                BLOCK_TYPE blockType = BLOCK_TYPE.getType(color);

                float invertedY = Utils.getGameHeight() - (y + 2.4f) * 2f;
                if (BLOCK_TYPE.BLOCK == blockType) {
                    possibleBlockList.add(new Vector2(x * ratioX, invertedY));
//                    createBlock(x * ratioX, invertedY, ratioX);
                }
            }
        }

        for (Vector2 position: possibleBlockList) {
            if (MathUtils.randomBoolean()) {
                createBlock(position.x, position.y, width);
                createBlock(Constants.GAME_WIDTH - position.x - width, position.y, width);
            }
        }
    }

    private void createBlock(float x, float y, float width) {
        Block block = new Block();
        block.dimension.x = width;
        block.position.set(x, y);
        block.initBody(world);

        levelBlocks.add(block);
        blocks.add(block);
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

    public void respawnBall() {
        ball.position.set(playerPad.position.x + playerPad.dimension.x / 2 - ball.dimension.x / 2,
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

    private void sweepTheDead() {
        if (world.isLocked()) {
            return;
        }

        for (Block block: destroyBlocks) {
            world.destroyBody(block.body);
            blocks.remove(block);

            block.body.setUserData(null);
            block.body = null;
            WorldController.shared.score++;
        }

        destroyBlocks.clear();
    }

    void destroy(Block block) {
        destroyBlocks.add(block);
    }

    public boolean isGameComplete() {
        return blocks.size() == 0;
    }
}
