package game;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Modal {
    protected boolean modalOpen;

    Modal() {
        this.modalOpen = false;
    }

    public void toggleModal() {
        modalOpen = !modalOpen;
    }

    public void draw(Graphics g, int screenWidth, int screenHeight) {
        g.setColor(Color.GRAY);
        g.fillRect(screenWidth / 2 - 300, screenHeight / 2 - 300, 600, 600);
        g.setColor(Color.BLACK);
        g.drawRect(screenWidth / 2 - 300, screenHeight / 2 - 300, 600, 600);
    }
}
