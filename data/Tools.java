package data;

import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;

import enums.ItemType;
import enums.ToolType;
import game.Requirements;

public class Tools {
    public static class ToolData {
        private Requirements requirements;
        private int damage;
        private int range;
        private double cooldown;
        private String imagePath;
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
                cachedImage = new ImageIcon(imagePath).getImage();
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

    private static final HashMap<ToolType, ToolData> tools = new HashMap<ToolType, ToolData>() {
        {
            put(ToolType.Pickaxe,
                    new ToolData(
                            new Requirements().addRequirement(ItemType.Stone, 5).addRequirement(ItemType.CoalOre, 2),
                            30, 5, 0.2, "assets/tool/pickaxe.png"));
            put(ToolType.Axe,
                    new ToolData(new Requirements().addRequirement(ItemType.Dirt, 4).addRequirement(ItemType.Stone, 2),
                            20, 3, 0.5, "assets/tool/axe.png"));
            put(ToolType.Shovel, new ToolData(new Requirements(), 2000, 1, 1, "assets/tool/shovel.png"));
        }
    };

    public static ToolData getToolData(ToolType type) {
        return tools.get(type);
    }

    public static HashMap<ToolType, ToolData> getTools() {
        return tools;
    }
}
