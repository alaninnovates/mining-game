package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

import data.Tools;
import data.Tools.ToolData;
import enums.ToolType;

public class Shop extends Modal {
    @Override
    public void draw(Graphics g, int screenWidth, int screenHeight) {
        if (!isModalOpen()) {
            return;
        }
        super.draw(g, screenWidth, screenHeight);
        g.setColor(Color.WHITE);
        g.drawString("Shop", screenWidth / 2 - 20, screenHeight / 2 - 280);
        int x = 100;
        int y = 100;
        int toolWidth = 50;
        int toolHeight = 50;
        int spacing = 20;

        for (Map.Entry<ToolType, ToolData> entry : Tools.getTools().entrySet()) {
            ToolData toolData = entry.getValue();
            g.setColor(Color.GREEN);
            g.drawImage(toolData.getImage(), x, y, toolWidth, toolHeight, null);
            g.drawString(entry.getKey().toString(), x, y + toolHeight + spacing);

            x += toolWidth + spacing;
            if (x + toolWidth > screenWidth) {
                x = 100;
                y += toolHeight + spacing;
            }
        }
    }
}
