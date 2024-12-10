import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

import data.Items;
import data.Items.ItemData;
import enums.ItemType;

public class Inventory extends Modal {
    private HashMap<ItemType, Integer> items;

    Inventory() {
        super();
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

    @Override
    public void draw(Graphics g, int screenWidth, int screenHeight) {
        if (!modalOpen) {
            return;
        }
        super.draw(g, screenWidth, screenHeight);
        g.setColor(Color.WHITE);
        g.drawString("Inventory", screenWidth / 2 - 20, screenHeight / 2 - 280);

        int matrixSize = 10;
        int matrixX = screenWidth / 2 - 280;
        int matrixY = screenHeight / 2 - 260;
        int matrixCellWidth = 60;
        int matrixCellHeight = 60;

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                int index = i * matrixSize + j;
                if (index >= items.size()) {
                    break;
                }

                ItemType itemType = (ItemType) items.keySet().toArray()[index];
                ItemData itemData = Items.getItemData(itemType);
                int itemAmount = items.get(itemType);

                g.drawImage(itemData.getImage(), matrixX + j * matrixCellWidth, matrixY + i * matrixCellHeight, null);
                g.drawString("x" + itemAmount, matrixX + j * matrixCellWidth + 40, matrixY + i * matrixCellHeight + 20);
            }
        }
    }
}