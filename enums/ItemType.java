package enums;

import java.awt.Image;

import javax.swing.ImageIcon;

public enum ItemType {
    Stone("assets/block/stone.png"),
    CoalOre("assets/item/coal.png"),
    Wood("assets/block/wood.png"),
    Dirt("assets/block/dirt.png"),
    DiamondOre("assets/item/diamond.png"),
    GoldOre("assets/item/gold.png");

    private String imagePath;
    private Image cachedImage;

    ItemType(String imagePath) {
        this.imagePath = imagePath;
    }

    public Image getImage() {
        if (cachedImage == null) {
            cachedImage = new ImageIcon(imagePath).getImage();
        }
        return cachedImage;
    }
}
