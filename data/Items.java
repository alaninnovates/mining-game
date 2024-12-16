package data;

import java.awt.Image;
import java.util.HashMap;

import assets.AssetLoader;
import enums.ItemType;

public class Items {
    public static class ItemData {
        private final int value;
        private final String imagePath;
        private Image cachedImage;

        ItemData(int value, String imagePath) {
            this.value = value;
            this.imagePath = imagePath;
        }

        public Image getImage() {
            if (cachedImage == null) {
                cachedImage = AssetLoader.loadImage(imagePath).getImage();
            }
            return cachedImage;
        }

        public int getValue() {
            return value;
        }
    }

    private static final HashMap<ItemType, ItemData> items = new HashMap<>() {
        {
            put(ItemType.Stone, new ItemData(5, "assets/block/stone.png"));
            put(ItemType.CoalOre, new ItemData(10, "assets/item/coal-ore.png"));
            put(ItemType.Wood, new ItemData(3, "assets/block/wood.png"));
            put(ItemType.Dirt, new ItemData(1, "assets/block/dirt.png"));
            put(ItemType.DiamondOre, new ItemData(25, "assets/item/diamond-ore.png"));
            put(ItemType.GoldOre, new ItemData(15, "assets/item/gold-ore.png"));
        }
    };

    public static ItemData getItemData(ItemType type) {
        return items.get(type);
    }
}
