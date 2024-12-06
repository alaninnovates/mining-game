import java.util.HashMap;

import enums.ItemType;

public class Inventory {
    private HashMap<ItemType, Integer> items;

    Inventory() {
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
}