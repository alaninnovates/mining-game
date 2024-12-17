package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import data.Items;
import data.Items.ItemData;
import enums.ItemType;
import ui.Button;
import ui.ButtonManager;
import ui.Modal;

public class Inventory extends Modal {
    private int coins;
    private int rebirths;
    private final HashMap<ItemType, Integer> items;

    Inventory() {
        super();
        this.coins = 0;
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
            if (items.get(type) <= 0) {
                items.remove(type);
            }
        }
    }

    public int getItemCount(ItemType type) {
        if (items.containsKey(type)) {
            return items.get(type);
        }
        return 0;
    }

    public void sellItem(ItemType type, int amount) {
        if (items.containsKey(type)) {
            int itemAmount = items.get(type);
            if (itemAmount >= amount) {
                if (itemAmount - amount == 0) {
                    items.remove(type);
                } else {
                    items.put(type, itemAmount - amount);
                }
                ItemData itemData = Items.getItemData(type);
                coins += itemData.getValue() * amount;
            }
        }
    }

    public int getCoins() {
        return coins;
    }

    public void rebirth() {
        rebirths++;
        items.clear();
    }

    public int getRebirths() {
        return rebirths;
    }

    @Override
    public void draw(Graphics g, int screenWidth, int screenHeight) {
        if (!isModalOpen()) {
            return;
        }
        super.draw(g, screenWidth, screenHeight);
        g.setColor(Color.WHITE);
        g.drawString("Inventory", screenWidth / 2 - 20, screenHeight / 2 - 280);
        g.drawString("Coins: " + coins, screenWidth / 2 - 280, screenHeight / 2 - 280);

        ArrayList<Button> buttons = new ArrayList<>();

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

                int cornerX = matrixX + j * matrixCellWidth;
                int cornerY = matrixY + i * matrixCellHeight;
                g.drawImage(itemData.getImage(), cornerX, cornerY, null);
                g.drawString("x" + itemAmount, cornerX + matrixCellWidth - 15, cornerY + matrixCellHeight - 15);
                buttons.add(
                        new Button(cornerX, cornerY, 20, 20, "$",
                                Color.GREEN, () -> sellItem(itemType, itemAmount)));
            }
        }
        ButtonManager.getInstance().setButtons("inventory", buttons);
    }

    @Override
    public void toggleModal() {
        super.toggleModal();
        ButtonManager.getInstance().removeButtons("inventory");
    }
}