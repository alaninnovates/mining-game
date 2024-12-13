package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

public class WarningToastManager {
    private class Toast {
        private String text;
        private int tick;
        private int expireMs;

        Toast(String text, int tick, int expireMs) {
            this.text = text;
            this.tick = tick;
            this.expireMs = expireMs;
        }

        public String getText() {
            return text;
        }

        public int getTick() {
            return tick;
        }

        public int getExpireMs() {
            return expireMs;
        }
    }

    private ArrayList<Toast> toasts;
    private static WarningToastManager instance;
    private int tick;

    private WarningToastManager() {
        toasts = new ArrayList<>();
    }

    public static WarningToastManager getInstance() {
        if (instance == null) {
            instance = new WarningToastManager();
        }
        return instance;
    }

    public void addToast(String toast, int expireMs) {
        toasts.add(new Toast(toast, tick, expireMs));
    }

    public void draw(Graphics g) {
        int x = 10;
        int y = 10;
        Iterator<Toast> iterator = toasts.iterator();
        while (iterator.hasNext()) {
            Toast t = iterator.next();
            if (t.getTick() + 60 * t.getExpireMs() / 1000 < tick) {
                iterator.remove();
                continue;
            }
            String toast = t.getText();
            int stringWidth = g.getFontMetrics().stringWidth(toast);
            g.setColor(Color.RED);
            g.fillRect(x, y, stringWidth + 10, 50);
            g.setColor(Color.WHITE);
            g.drawString(toast, x + 5, y + 30);
            y += 60;
        }
        tick++;
    }
}
