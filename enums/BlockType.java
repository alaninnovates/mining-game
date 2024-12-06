package enums;

import java.awt.Image;

import javax.swing.ImageIcon;

public enum BlockType {
    Grass(100, "assets/block/grass.png", ItemType.Dirt),
    Dirt(100, "assets/block/dirt.png", ItemType.Dirt),
    Stone(200, "assets/block/stone.png", ItemType.Stone),
    Coal(300, "assets/block/coal.png", ItemType.CoalOre),
    Water(0, "assets/block/water.png"),
    Air(0, "assets/block/air.png"),
    Gold(400, "assets/block/gold.png", ItemType.GoldOre),
    Diamond(500, "assets/block/diamond.png", ItemType.DiamondOre);

    private int health;
    private String imagePath;
    private Image cachedImage;
    private ItemType drop;

    BlockType(int health, String imagePath) {
        this.health = health;
        this.imagePath = imagePath;
    }

    BlockType(int health, String imagePath, ItemType drop) {
        this.health = health;
        this.imagePath = imagePath;
        this.drop = drop;
    }

    public int getHealth() {
        return health;
    }

    public Image getImage() {
        if (cachedImage == null) {
            cachedImage = new ImageIcon(imagePath).getImage();
        }
        return cachedImage;
    }

    public ItemType getDrop() {
        return drop;
    }

    public boolean dropsItem() {
        return drop != null;
    }
}