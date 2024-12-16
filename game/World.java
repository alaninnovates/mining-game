package game;

import java.awt.Graphics;

import enums.BlockType;

import java.awt.Color;

public class World {
    private final int width;
    private final int height;
    private final Block[][] blocks;
    private final Player player;

    World(int width, int height) {
        this.width = width;
        this.height = height;
        this.player = new Player(width, height, this);
        blocks = new Block[width / 50][height / 50];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (j < 2) {
                    blocks[i][j] = new Block(BlockType.Air, i * 50, j * 50);
                } else if (j == 2) {
                    blocks[i][j] = new Block(BlockType.Grass, i * 50, j * 50);
                } else if (j == 3) {
                    blocks[i][j] = new Block(BlockType.Dirt, i * 50, j * 50);
                } else {
                    double randVal = Math.random();
                    if (j < 12) {
                        blocks[i][j] = new Block(randVal > 0.5 ? BlockType.Dirt : BlockType.Stone, i * 50, j * 50);
                    } else {
                        if (randVal < 0.5) {
                            blocks[i][j] = new Block(BlockType.Stone, i * 50, j * 50);
                        } else if (randVal < 0.90) {
                            blocks[i][j] = new Block(BlockType.Coal, i * 50, j * 50);
                        } else if (randVal < 0.95) {
                            blocks[i][j] = new Block(BlockType.Gold, i * 50, j * 50);
                        } else {
                            blocks[i][j] = new Block(BlockType.Diamond, i * 50, j * 50);
                        }
                    }
                }
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        for (Block[] blockRow : blocks) {
            for (Block block : blockRow) {
                block.draw(g);
            }
        }
        player.draw(g);
    }

    public Player getPlayer() {
        return player;
    }

    public Block[][] getBlocks() {
        return blocks;
    }
}
