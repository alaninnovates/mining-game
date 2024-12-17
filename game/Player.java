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
import exceptions.RebirthException;
import exceptions.NotPurchasedException;
import exceptions.RequirementsException;

public class Player {
    private int posX, posY;
    private final int screenWidth;
    private final int screenHeight;
    private HashMap<ToolType, Tool> allTools;
    private ToolType currentTool;
    private final Inventory inventory;
    private final Image playerImage;
    private final World world;

    public Player(int screenWidth, int screenHeight, World world) {
        posX = 0;
        posY = 0;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        initializeTools();
        inventory = new Inventory();
        playerImage = AssetLoader.loadImage("assets/character-new.png").getImage();
        this.world = world;
    }

    private void initializeTools() {
        allTools = new HashMap<>();
        for (ToolType type : ToolType.values()) {
            allTools.put(type, new Tool(type));
        }
        currentTool = ToolType.Shovel;
        allTools.get(currentTool).purchase(inventory);
    }

    public void draw(Graphics g) {
        g.drawImage(playerImage, screenWidth / 2, screenHeight / 2, null);
        allTools.get(currentTool).draw(g, screenWidth / 2, screenHeight / 2);
        inventory.draw(g, screenWidth, screenHeight);
    }

    public void moveRight() {
        if (world.getBlock(posX + 50, posY).getType() != BlockType.Air) {
            return;
        }
        posX += 50;
    }

    public void moveLeft() {
        if (world.getBlock(posX - 50, posY).getType() != BlockType.Air) {
            return;
        }
        posX -= 50;
    }

    public void moveUp() {
        if (world.getBlock(posX, posY - 50).getType() != BlockType.Air) {
            return;
        }
        posY -= 50;
    }

    public void moveDown() {
        if (world.getBlock(posX, posY + 50).getType() != BlockType.Air) {
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

    public void rebirth() {
        int requiredAmt = RebirthCalculator.calculateRebirthCost(inventory.getRebirths());
        if (inventory.getCoins() < requiredAmt) {
            throw new RebirthException(requiredAmt - inventory.getCoins());
        }
        posX = 0;
        posY = 0;
        initializeTools();
        inventory.rebirth();
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