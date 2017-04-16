package com.andrej.igra.control;

import com.andrej.igra.Constants;
import com.andrej.igra.Utils;
import com.andrej.igra.gameobjects.Block;
import com.andrej.igra.gameobjects.Player;
import com.andrej.igra.gameobjects.HorizontalBorder;
import com.andrej.igra.gameobjects.TopBorder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    public ArrayList<Block> blocks;
    public Player player;
    public TopBorder topBorder;
    public HorizontalBorder leftBorder;
    public HorizontalBorder rightBorder;

    public Level() {
        blocks = new ArrayList<Block>();

        load("level00.png");
        createPlayer();
        buildWalls();
    }

    private void createPlayer() {
        player = new Player();
        player.position.set(Constants.GAME_WIDTH / 2 - player.dimension.x / 2, 2);
    }

    private void buildWalls() {
        topBorder = new TopBorder();
        topBorder.position.set(topBorder.dimension.y / 2,
                Utils.getGameHeight() - topBorder.dimension.y);
        leftBorder = new HorizontalBorder();
        rightBorder = new HorizontalBorder();
        rightBorder.scale.x = -1;
        rightBorder.position.set(Constants.GAME_WIDTH - rightBorder.dimension.x, 0);
    }

    public void update(float delta) {
        player.update(delta);
    }

    public void render(SpriteBatch batch) {

        topBorder.render(batch);
        leftBorder.render(batch);
        rightBorder.render(batch);

        for (Block block: blocks) {
            block.render(batch);
        }

        player.render(batch);
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
}
