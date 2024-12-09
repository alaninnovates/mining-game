import java.awt.Graphics;
import java.awt.Image;
import java.util.Map;

import javax.swing.ImageIcon;

import enums.ToolType;
import exceptions.RequirementsException;
import enums.ItemType;

public class Tool {
    private ToolType type;
    private Requirements requirements;
    private int damage;
    private int range;
    private double cooldown;
    private boolean purchased;
    private Image image;

    Tool(ToolType type, Requirements requirements, int damage, int range, double cooldown) {
        this.type = type;
        this.requirements = requirements;
        this.damage = damage;
        this.range = range;
        this.cooldown = cooldown;
        this.image = new ImageIcon("assets/tool/pickaxe.png").getImage();
    }

    public void draw(Graphics g, int posX, int posY) {
        g.drawImage(image, posX - 5, posY + 15, null);
    }

    public boolean canPurchase(Inventory inventory) {
        return requirements.fulfillsRequirements(inventory);
    }

    public void purchase(Inventory inventory) {
        if (!canPurchase(inventory)) {
            throw new RequirementsException();
        }
        for (Map.Entry<ItemType, Integer> entry : requirements.getRequirements().entrySet()) {
            ItemType type = entry.getKey();
            int quantity = entry.getValue();
            inventory.removeItem(type, quantity);
        }
        purchased = true;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public double getCooldown() {
        return cooldown;
    }

    public ToolType getType() {
        return type;
    }
}