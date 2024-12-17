package game;

import java.awt.Color;
import java.awt.Graphics;

import data.Blocks;
import data.Blocks.BlockData;
import enums.BlockType;

public class Block {
    private int health;
    private BlockType type;
    private BlockData blockData;
    private final int posX;
    private final int posY;
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
        g.drawImage(blockData.getImage(), posX, posY, null);
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
        int damageMultiplier = RebirthCalculator.calculateRebirthDamageMultiplier(inventory.getRebirths());
        health -= tool.getToolData().getDamage() * damageMultiplier;
        if (health <= 0) {
            int blockMultiplier = RebirthCalculator.calculateRebirthBlockMutiplier(inventory.getRebirths());
            inventory.addItem(blockData.getDrop(), blockMultiplier);
            resetBlockData(BlockType.Air);
            showHealthBar = false;
        } else {
            showHealthBar = true;
            healthBarTicks = 100;
        }
    }

    public BlockType getType() {
        return type;
    }

    public BlockData getBlockData() {
        return blockData;
    }

    private void resetBlockData(BlockType type) {
        this.type = type;
        blockData = Blocks.getBlockData(type);
        health = blockData.getHealth();
    }
}