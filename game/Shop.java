package game;

import java.awt.Color;
import java.awt.Graphics;

public class Shop extends Modal {
    @Override
    public void draw(Graphics g, int screenWidth, int screenHeight) {
        if (!modalOpen) {
            return;
        }
        super.draw(g, screenWidth, screenHeight);
        g.setColor(Color.WHITE);
        g.drawString("Shop", screenWidth / 2 - 20, screenHeight / 2 - 280);
    }
}
