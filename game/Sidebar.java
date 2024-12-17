package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import exceptions.RebirthException;
import ui.Button;
import ui.ButtonManager;
import ui.WarningToastManager;

public class Sidebar {
    private final int screenWidth;
    private final int screenHeight;
    private final Player player;
    private final Shop shop;
    private final Inventory inventory;

    public Sidebar(int screenWidth, int screenHeight, Player player, Shop shop, Inventory inventory) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.player = player;
        this.shop = shop;
        this.inventory = inventory;
    }

    public void draw(Graphics g) {
        int posX = screenWidth - 75;
        int posY = screenHeight / 2 - 100;
        g.setColor(Color.GRAY);
        g.fillRect(posX - 10, posY - 10, 70, 190);
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(new Button(posX, posY, 50, 50, "Rebirth", Color.BLUE,
                () -> {
                    try {
                        player.rebirth();
                    } catch (RebirthException e) {
                        WarningToastManager.getInstance().addToast(e.getMessage(), 5000);
                    }
                }));
        posY += 60;
        buttons.add(new Button(posX, posY, 50, 50, "Inventory", Color.BLUE,
                () -> {
                    inventory.toggleModal();
                }));
        posY += 60;
        buttons.add(new Button(posX, posY, 50, 50, "Shop", Color.BLUE,
                () -> {
                    shop.toggleModal();
                }));
        ButtonManager.getInstance().setButtons("sidebar", buttons);
    }
}
