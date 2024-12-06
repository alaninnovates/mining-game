import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import enums.ItemType;

public class Inventory {
    private boolean inventoryOpen;
    private HashMap<ItemType, Integer> items;

    Inventory() {
        this.inventoryOpen = false;
        this.items = new HashMap<>();
    }

    public void addItem(ItemType type, int amount) {
        if (items.containsKey(type)) {
            items.put(type, items.get(type) + amount);
        } else {
            items.put(type, amount);
        }
    }

    public void removeItem(ItemType type, int amount) {
        if (items.containsKey(type)) {
            items.put(type, items.get(type) - amount);
        }
    }

    public int getItemCount(ItemType type) {
        if (items.containsKey(type)) {
            return items.get(type);
        }
        return 0;
    }

    public void toggleInventoryOpen() {
        inventoryOpen = !inventoryOpen;
    }

    public void draw(Graphics g, int screenWidth, int screenHeight) {
        if (!inventoryOpen) {
            return;
        }
        g.setColor(Color.GRAY);
        g.fillRect(screenWidth / 2 - 300, screenHeight / 2 - 300, 600, 600);
        g.setColor(Color.BLACK);
        g.drawRect(screenWidth / 2 - 300, screenHeight / 2 - 300, 600, 600);
        g.setColor(Color.WHITE);
        g.drawString("Inventory", screenWidth / 2 - 20, screenHeight / 2 - 280);
        int y = screenHeight / 2 - 260;
        for (Map.Entry<ItemType, Integer> entry : items.entrySet()) {
            g.drawImage(entry.getKey().getImage(), screenWidth / 2 - 280, y, null);
            g.drawString("x" + entry.getValue(), screenWidth / 2 - 240, y + 20);
            y += 60;
        }
    }
}