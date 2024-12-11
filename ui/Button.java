package ui;

import java.awt.Color;
import java.awt.Graphics;

public class Button {
    private int x;
    private int y;
    private int width;
    private int height;
    private String text;
    private Color color;
    private Runnable onClick;

    public Button(int x, int y, int width, int height, String text, Color color, Runnable onClick) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.color = color;
        this.onClick = onClick;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        int stringWidth = g.getFontMetrics().stringWidth(text);
        int stringHeight = g.getFontMetrics().getHeight();
        g.drawString(text, x + width / 2 - stringWidth / 2, y + height / 2 + stringHeight / 4);
    }

    public boolean isClicked(int x, int y) {
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
    }

    public void onClick() {
        onClick.run();
    }
}
