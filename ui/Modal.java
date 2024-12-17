package ui;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Modal {
    private boolean modalOpen;

    protected Modal() {
        this.modalOpen = false;
    }

    public void toggleModal() {
        boolean modalCurrOpen = ModalManager.getInstance().isAnyModalOpen();

        if (modalOpen && modalCurrOpen) {
            ModalManager.getInstance().setAnyModalOpen(false);
            modalOpen = false;
        } else if (!modalOpen && !modalCurrOpen) {
            ModalManager.getInstance().setAnyModalOpen(true);
            modalOpen = true;
        }
    }

    public boolean isModalOpen() {
        return modalOpen;
    }

    public void draw(Graphics g, int screenWidth, int screenHeight) {
        g.setColor(Color.GRAY);
        g.fillRect(screenWidth / 2 - 300, screenHeight / 2 - 300, 600, 600);
        g.setColor(Color.BLACK);
        g.drawRect(screenWidth / 2 - 300, screenHeight / 2 - 300, 600, 600);
    }
}
