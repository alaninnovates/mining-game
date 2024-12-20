package game;

import java.awt.Graphics;

import enums.BlockType;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class World {
    private final int width;
    private final int height;
    private final Map<String, Block> blocks;
    private final Player player;

    World(int width, int height) {
        this.width = width;
        this.height = height;
        this.player = new Player(width, height, this);
        this.blocks = new HashMap<>();
        generateInitialBlocks();
    }

    private void generateInitialBlocks() {
        for (int x = -width / 2; x < width / 2; x += 50) {
            for (int y = -height / 2; y < height / 2; y += 50) {
                generateBlock(x, y);
            }
        }
    }

    private void generateBlock(int x, int y) {
        String key = getKey(x, y);
        if (!blocks.containsKey(key)) {
            BlockType type = determineBlockType(y);
            blocks.put(key, new Block(type, x, y));
        }
    }

    private BlockType determineBlockType(int y) {
        if (y < 50) {
            return BlockType.Air;
        } else if (y < 100) {
            return BlockType.Grass;
        } else if (y < 200) {
            return BlockType.Dirt;
        } else if (y < 12 * 50) {
            double randVal = Math.random();
            return randVal > 0.5 ? BlockType.Dirt : BlockType.Stone;
        } else {
            double randVal = Math.random();
            if (randVal < 0.5) {
                return BlockType.Stone;
            } else if (randVal < 0.9) {
                return BlockType.Coal;
            } else if (randVal < 0.95) {
                return BlockType.Gold;
            } else {
                return BlockType.Diamond;
            }
        }
    }

    private String getKey(int x, int y) {
        return x + "," + y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        int playerX = player.getPosX();
        int playerY = player.getPosY();
        for (int x = playerX - 400; x < playerX + 400; x += 50) {
            for (int y = playerY - 400; y < playerY + 400; y += 50) {
                Block block = getBlock(x, y);
                block.draw(g, playerX, playerY);
            }
        }
        player.draw(g);
    }

    public Player getPlayer() {
        return player;
    }

    public Block getBlock(int x, int y) {
        if (blocks.containsKey(getKey(x, y))) {
            return blocks.get(getKey(x, y));
        }
        generateBlock(x, y);
        return blocks.get(getKey(x, y));
    }
}
