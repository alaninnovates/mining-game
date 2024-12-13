package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import java.awt.Graphics;
import java.awt.Image;

import assets.AssetLoader;
import enums.BlockType;
import enums.ToolType;
import exceptions.PurchasedException;
import exceptions.NotPurchasedException;
import exceptions.RequirementsException;

public class Player {
    private int posX, posY;
    private int screenWidth, screenHeight;
    private HashMap<ToolType, Tool> allTools;
    private ToolType currentTool;
    private Inventory inventory;
    private Image playerImage;
    private World world;

    public Player(int screenWidth, int screenHeight, World world) {
        posX = 0;
        posY = 0;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        allTools = new HashMap<>();
        for (ToolType type : ToolType.values()) {
            allTools.put(type, new Tool(type));
        }
        currentTool = ToolType.Shovel;
        allTools.get(currentTool).purchase(inventory);
        inventory = new Inventory();
        playerImage = AssetLoader.loadImage("assets/character-new.png").getImage();
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

    public void equipTool(ToolType toolType) throws NotPurchasedException {
        if (allTools.get(toolType).isPurchased()) {
            currentTool = toolType;
        } else {
            throw new NotPurchasedException();
        }
    }

    public void purchaseTool(ToolType toolType) throws PurchasedException, RequirementsException {
        if (allTools.get(toolType).isPurchased()) {
            throw new PurchasedException();
        }
        allTools.get(toolType).purchase(inventory);
        currentTool = toolType;
    }

    public ArrayList<ToolType> getPurchasedTools() {
        return allTools.entrySet().stream()
            .filter(entry -> entry.getValue().isPurchased())
            .map(Map.Entry::getKey)
            .collect(Collectors.toCollection(ArrayList::new));
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