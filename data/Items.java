package data;

import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;

import enums.ItemType;

public class Items {
    public static class ItemData {
        private String imagePath;
        private Image cachedImage;

        ItemData(String imagePath) {
            this.imagePath = imagePath;
        }

        public Image getImage() {
            if (cachedImage == null) {
                cachedImage = new ImageIcon(imagePath).getImage();
            }
            return cachedImage;
        }
    }

    private static final HashMap<ItemType, ItemData> items = new HashMap<ItemType, ItemData>() {
        {
            put(ItemType.Stone, new ItemData("assets/block/stone.png"));
            put(ItemType.CoalOre, new ItemData("assets/item/coal-ore.png"));
            put(ItemType.Wood, new ItemData("assets/block/wood.png"));
            put(ItemType.Dirt, new ItemData("assets/block/dirt.png"));
            put(ItemType.DiamondOre, new ItemData("assets/item/diamond-ore.png"));
            put(ItemType.GoldOre, new ItemData("assets/item/gold-ore.png"));
        }
    };

    public static ItemData getItemData(ItemType type) {
        return items.get(type);
    }
}
