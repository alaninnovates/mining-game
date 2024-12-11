package game;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Map;

import data.Tools;
import data.Tools.ToolData;
import enums.ToolType;
import exceptions.RequirementsException;
import enums.ItemType;

public class Tool {
    private ToolType type;
    private ToolData toolData;
    private boolean purchased;

    Tool(ToolType type) {
        this.type = type;
        this.toolData = Tools.getToolData(type);
    }

    public void draw(Graphics g, int posX, int posY) {
        g.drawImage(toolData.getImage(), posX - 5, posY + 15, null);
    }

    public boolean canPurchase(Inventory inventory) {
        return toolData.getRequirements().fulfillsRequirements(inventory);
    }

    public void purchase(Inventory inventory) {
        if (!canPurchase(inventory)) {
            throw new RequirementsException();
        }
        for (Map.Entry<ItemType, Integer> entry : toolData.getRequirements().getRequirements().entrySet()) {
            ItemType type = entry.getKey();
            int quantity = entry.getValue();
            inventory.removeItem(type, quantity);
        }
        purchased = true;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public ToolData getToolData() {
        return toolData;
    }
}