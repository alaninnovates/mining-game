package data;

import java.awt.Image;
import java.util.HashMap;

import assets.AssetLoader;
import enums.ItemType;
import enums.ToolType;
import game.Requirements;

public class Tools {
    public static class ToolData {
        private final Requirements requirements;
        private final int damage;
        private final int range;
        private final double cooldown;
        private final String imagePath;
        private Image cachedImage;

        ToolData(Requirements requirements, int damage, int range, double cooldown, String imagePath) {
            this.requirements = requirements;
            this.damage = damage;
            this.range = range;
            this.cooldown = cooldown;
            this.imagePath = imagePath;
        }

        public Image getImage() {
            if (cachedImage == null) {
                cachedImage = AssetLoader.loadImage(imagePath).getImage();
            }
            return cachedImage;
        }

        public Requirements getRequirements() {
            return requirements;
        }

        public int getDamage() {
            return damage;
        }

        public int getRange() {
            return range;
        }

        public double getCooldown() {
            return cooldown;
        }
    }

    private static final HashMap<ToolType, ToolData> tools = new HashMap<>() {
        {
            put(ToolType.Pickaxe,
                    new ToolData(
                            new Requirements().addRequirement(ItemType.Stone, 5).addRequirement(ItemType.CoalOre, 2),
                            40, 5, 0.2, "assets/tool/pickaxe.png"));
            put(ToolType.Axe,
                    new ToolData(new Requirements().addRequirement(ItemType.Dirt, 4).addRequirement(ItemType.Stone, 2),
                            30, 3, 0.5, "assets/tool/axe.png"));
            put(ToolType.Shovel, new ToolData(new Requirements(), 15, 1, 1, "assets/tool/shovel.png"));
        }
    };

    public static ToolData getToolData(ToolType type) {
        return tools.get(type);
    }

    public static HashMap<ToolType, ToolData> getTools() {
        return tools;
    }
}
