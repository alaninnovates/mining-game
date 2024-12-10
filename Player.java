import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import enums.BlockType;
import enums.ItemType;
import enums.ToolType;
import exceptions.NotPurchasedException;
import exceptions.RequirementsException;

public class Player {
    private int posX, posY;
    private int screenWidth, screenHeight;
    private Tool[] tools;
    private int currentTool;
    private Inventory inventory;
    private Image playerImage;
    private World world;

    public Player(int screenWidth, int screenHeight, World world) {
        posX = 0;
        posY = 0;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        tools = new Tool[3];
        tools[2] = new Tool(ToolType.Pickaxe,
                new Requirements().addRequirement(ItemType.Stone, 5).addRequirement(ItemType.CoalOre, 2), 30, 5, 0.2);
        tools[1] = new Tool(ToolType.Axe,
                new Requirements().addRequirement(ItemType.Dirt, 4).addRequirement(ItemType.Stone, 2), 20, 3, 0.5);
        tools[0] = new Tool(ToolType.Shovel, new Requirements(), 2000, 1, 1);
        currentTool = 0;
        inventory = new Inventory();
        playerImage = new ImageIcon("assets/character.png").getImage();
        this.world = world;
    }

    public void draw(Graphics g) {
        g.drawImage(playerImage, posX, posY, null);
        tools[currentTool].draw(g, posX, posY);
        inventory.draw(g, screenWidth, screenHeight);
    }

    public void moveRight() {
        if (posX + 50 >= screenWidth) {
            return;
        }
        if (world.getBlocks()[posX / 50 + 1][posY / 50].getType() != BlockType.Air) {
            return;
        }
        posX += 50;
    }

    public void moveLeft() {
        if (posX - 50 < 0) {
            return;
        }
        if (world.getBlocks()[posX / 50 - 1][posY / 50].getType() != BlockType.Air) {
            return;
        }
        posX -= 50;
    }

    public void moveUp() {
        if (posY - 50 < 0) {
            return;
        }
        if (world.getBlocks()[posX / 50][posY / 50 - 1].getType() != BlockType.Air) {
            return;
        }
        posY -= 50;
    }

    public void moveDown() {
        if (posY + 50 >= screenHeight) {
            return;
        }
        if (world.getBlocks()[posX / 50][posY / 50 + 1].getType() != BlockType.Air) {
            return;
        }
        posY += 50;
    }

    public void equipTool(int index) throws NotPurchasedException {
        if (index < 0 || index >= tools.length) {
            return;
        }
        if (!tools[index].isPurchased()) {
            throw new NotPurchasedException();
        }
        currentTool = index;
    }

    public void purchaseTool() {
        try {
            tools[currentTool].purchase(inventory);
        } catch (RequirementsException e) {
            WarningToast.getInstance().addToast(e.getMessage());
        }
    }

    public Tool getCurrentTool() {
        return tools[currentTool];
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Inventory getInventory() {
        return inventory;
    }
}