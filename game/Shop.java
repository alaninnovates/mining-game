package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Map;

import data.Tools;
import data.Tools.ToolData;
import enums.ItemType;
import enums.ToolType;
import exceptions.NotPurchasedException;
import exceptions.PurchasedException;
import exceptions.RequirementsException;
import ui.Button;
import ui.ButtonManager;
import ui.Modal;
import ui.WarningToastManager;

public class Shop extends Modal {
    Player player;

    public Shop(Player player) {
        super();
        this.player = player;
    }


    @Override
    public void draw(Graphics g, int screenWidth, int screenHeight) {
        if (!isModalOpen()) {
            return;
        }
        super.draw(g, screenWidth, screenHeight);
        g.setColor(Color.WHITE);
        g.drawString("Shop", screenWidth / 2 - 20, screenHeight / 2 - 280);
        int x = screenWidth / 2 - 280;
        int y = screenHeight / 2 - 260;
        int toolWidth = 50;
        int toolHeight = 50;
        int gap = 140-toolWidth;

        ArrayList<Button> buttons = new ArrayList<>();

        for (Map.Entry<ToolType, ToolData> entry : Tools.getTools().entrySet()) {
            ToolData toolData = entry.getValue();
            g.drawImage(toolData.getImage(), x, y, toolWidth, toolHeight, null);
            int currY = y + toolHeight + 20;
            g.drawString(entry.getKey().toString(), x, currY);

            Requirements requirements = toolData.getRequirements();

            currY += 5;
            for (Map.Entry<ItemType, Integer> requirementsMap : requirements.getRequirements().entrySet()) {
                ItemType itemType = requirementsMap.getKey();
                int amount = requirementsMap.getValue();
                currY += 15;
                g.drawString(itemType.toString() + " x" + amount, x, currY);
            }

            Color color;
            String buttonText;
            if (requirements.fulfillsRequirements(player.getInventory())) {
                color = Color.GREEN;
                buttonText = "Buy";
            } else {
                color = Color.RED;
                buttonText = "Cannot afford";
            }
            if (player.getPurchasedTools().contains(entry.getKey())) {
                color = Color.GREEN;
                buttonText = "Equip";
            }
            if (player.getCurrentTool().getType().equals(entry.getKey())) {
                color = Color.BLACK;
                buttonText = "Equipped";
            }

            int buttonTextWidth = g.getFontMetrics().stringWidth(buttonText);

            currY += 10;
            buttons.add(
                    new Button(x, currY, buttonTextWidth + 10, 20, buttonText,
                            color, () -> {
                        try {
                            player.purchaseTool(entry.getKey());
                        } catch (RequirementsException e) {
                            WarningToastManager.getInstance().addToast(e.getMessage(), 5000);
                        } catch (PurchasedException e) {
                            try {
                                player.equipTool(entry.getKey());
                            } catch (NotPurchasedException ignored) {
                            }
                        }
                    }));

            x += toolWidth + gap;
            if (x + toolWidth > screenWidth) {
                x = 100;
                y += toolHeight + 100;
            }
        }

        ButtonManager.getInstance().setButtons("shop", buttons);
    }

    @Override
    public void toggleModal() {
        super.toggleModal();
        ButtonManager.getInstance().removeButtons("shop");
    }
}
