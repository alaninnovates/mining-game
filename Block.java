import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import data.Blocks;
import data.Blocks.BlockData;
import enums.BlockType;

public class Block {
    private int health;
    private BlockType type;
    private BlockData blockData;
    private int posX, posY;
    private Image image;
    private boolean showHealthBar;
    private int healthBarTicks;

    Block(BlockType type, int posX, int posY) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        resetBlockData(type);
        this.showHealthBar = false;
        this.healthBarTicks = 0;
    }

    public void draw(Graphics g) {
        g.drawImage(image, posX, posY, null);
        if (showHealthBar) {
            drawHealthBar(g);
        }
    }

    public void drawHealthBar(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(posX + 5, posY + 20, 40, 5);
        g.setColor(Color.GREEN);
        g.fillRect(posX + 5, posY + 20, (int) (40 * ((double) health / blockData.getHealth())), 5);
        healthBarTicks--;
        if (healthBarTicks <= 0) {
            showHealthBar = false;
        }
    }

    public void decreaseHealthWith(Tool tool, Inventory inventory) {
        health -= tool.getDamage();
        if (health <= 0) {
            inventory.addItem(blockData.getDrop(), 1);
            resetBlockData(BlockType.Air);
            showHealthBar = false;
        } else {
            showHealthBar = true;
            healthBarTicks = 100;
        }
    }

    public boolean isBroken() {
        return health <= 0;
    }

    public BlockType getType() {
        return type;
    }

    public BlockData getBlockData() {
        return blockData;
    }

    public void update() {
        if (showHealthBar) {
            healthBarTicks--;
            if (healthBarTicks <= 0) {
                showHealthBar = false;
            }
        }
    }

    public int getHealth() {
        return health;
    }

    private void resetBlockData(BlockType type) {
        blockData = Blocks.getBlockData(type);
        health = blockData.getHealth();
        image = blockData.getImage();
    }
}