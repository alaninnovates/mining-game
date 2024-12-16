package ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

public class ButtonManager {
    private final HashMap<String, ArrayList<Button>> buttonGroups;
    private static ButtonManager instance;

    private ButtonManager() {
        buttonGroups = new HashMap<>();
    }

    public static ButtonManager getInstance() {
        if (instance == null) {
            instance = new ButtonManager();
        }
        return instance;
    }

    public boolean mousePressed(int x, int y) {
        for (ArrayList<Button> buttons : buttonGroups.values()) {
            for (Button button : buttons) {
                if (button.isClicked(x, y)) {
                    button.onClick();
                    return true;
                }
            }
        }
        return false;
    }

    public void setButtons(String group, ArrayList<Button> buttons) {
        buttonGroups.put(group, buttons);
    }

    public void removeButtons(String group) {
        buttonGroups.remove(group);
    }

    public void draw(Graphics g) {
        for (ArrayList<Button> buttons : buttonGroups.values()) {
            for (Button button : buttons) {
                button.draw(g);
            }
        }
    }
}
