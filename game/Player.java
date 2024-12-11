package game;

import java.util.ArrayList;
import java.util.stream.Collectors;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import enums.BlockType;
import enums.ToolType;
import exceptions.NotPurchasedException;
import exceptions.RequirementsException;

public class Player {
    private int posX, posY;
    private int screenWidth, screenHeight;
    private ArrayList<Tool> allTools;
    private int currentTool;
    private Inventory inventory;
    private Image playerImage;
    private World world;

    public Player(int screenWidth, int screenHeight, World world) {
        posX = 0;
        posY = 0;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        allTools = new ArrayList<>();
        for (ToolType type : ToolType.values()) {
            allTools.add(new Tool(type));
        }
        currentTool = allTools.stream().map(Tool::getType).collect(Collectors.toList()).indexOf(ToolType.Shovel);
        inventory = new Inventory();
        playerImage = new ImageIcon("assets/character-new.png").getImage();
        this.world = world;
    }

    public void draw(Graphics g) {
        g.drawImage(playerImage, posX, posY, null);
        allTools.get(currentTool).draw(g, posX, posY);
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
        if (index < 0 || index >= allTools.size()) {
            return;
        }
        if (!allTools.get(index).isPurchased()) {
            throw new NotPurchasedException();
        }
        currentTool = index;
    }

    public void purchaseTool() {
        try {
            allTools.get(currentTool).purchase(inventory);
        } catch (RequirementsException e) {
            WarningToast.getInstance().addToast(e.getMessage());
        }
    }

    public Tool getCurrentTool() {
        return allTools.get(currentTool);
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