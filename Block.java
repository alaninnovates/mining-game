import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import enums.BlockType;

public class Block {
    private int health;
    private BlockType type;
    private int posX, posY;
    private Image image;
    private boolean showHealthBar;
    private int healthBarTicks;

    Block(BlockType type, int posX, int posY) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.health = type.getHealth();
        this.image = type.getImage();
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
        g.fillRect(posX + 5, posY + 20, (int) (40 * ((double) health / type.getHealth())), 5);
        healthBarTicks--;
        if (healthBarTicks <= 0) {
            showHealthBar = false;
        }
    }

    public void decreaseHealthWith(Tool tool, Inventory inventory) {
        health -= tool.getDamage();
        if (health <= 0) {
            inventory.addItem(type.getDrop(), 1);
            type = BlockType.Air;
            image = type.getImage();
            health = 0;
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
}