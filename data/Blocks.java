package data;

import java.awt.Image;
import java.util.HashMap;

import assets.AssetLoader;
import enums.BlockType;
import enums.ItemType;

public class Blocks {
    public static class BlockData {
        private final int health;
        private final String imagePath;
        private final ItemType drop;
        private Image cachedImage;

        BlockData(int health, String imagePath) {
            this(health, imagePath, null);
        }

        BlockData(int health, String imagePath, ItemType drop) {
            this.health = health;
            this.imagePath = imagePath;
            this.drop = drop;
        }

        public int getHealth() {
            return health;
        }

        public Image getImage() {
            if (cachedImage == null) {
                cachedImage = AssetLoader.loadImage(imagePath).getImage();
            }
            return cachedImage;
        }

        public ItemType getDrop() {
            return drop;
        }

        public boolean isBreakable() {
            return drop != null;
        }
    }

    private static final HashMap<BlockType, BlockData> blocks = new HashMap<>() {
        {
            put(BlockType.Grass, new BlockData(100, "assets/block/grass.png", ItemType.Dirt));
            put(BlockType.Dirt, new BlockData(100, "assets/block/dirt.png", ItemType.Dirt));
            put(BlockType.Stone, new BlockData(200, "assets/block/stone.png", ItemType.Stone));
            put(BlockType.Fossil, new BlockData(200, "assets/block/fossil.png", ItemType.Fossil));
            put(BlockType.Coal, new BlockData(300, "assets/block/coal.png", ItemType.CoalOre));
            put(BlockType.Water, new BlockData(0, "assets/block/water.png"));
            put(BlockType.Air, new BlockData(0, "assets/block/air.png"));
            put(BlockType.Gold, new BlockData(400, "assets/block/gold.png", ItemType.GoldOre));
            put(BlockType.Diamond, new BlockData(500, "assets/block/diamond.png", ItemType.DiamondOre));
        }
    };

    public static BlockData getBlockData(BlockType t) {
        return blocks.get(t);
    }
}
