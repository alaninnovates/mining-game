import java.util.HashMap;

import enums.ItemType;

public class Requirements {
    private HashMap<ItemType, Integer> requirements;

    Requirements() {
        this.requirements = new HashMap<>();
    }

    public Requirements addRequirement(ItemType type, int amount) {
        if (requirements.containsKey(type)) {
            throw new IllegalArgumentException("Requirement already exists");
        }
        requirements.put(type, amount);
        return this;
    }

    public HashMap<ItemType, Integer> getRequirements() {
        return requirements;
    }

    public boolean fulfillsRequirements(Inventory inventory) {
        for (ItemType type : requirements.keySet()) {
            if (inventory.getItemCount(type) < requirements.get(type)) {
                return false;
            }
        }
        return true;
    }
}