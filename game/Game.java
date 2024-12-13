package game;

import javax.swing.JPanel;

import data.Tools.ToolData;
import ui.ButtonManager;
import ui.WarningToastManager;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game extends JPanel implements KeyListener, MouseListener {
    private World world;
    private Shop shop;
    private WarningToastManager warningToast;
    private ButtonManager buttonManager;
    private int width = 800;
    private int height = 800;
    private int cooldownTicks = 0;

    public Game() {
        super();
        new Updater(this);
        world = new World(width, height);
        shop = new Shop(world.getPlayer());
        warningToast = WarningToastManager.getInstance();
        buttonManager = ButtonManager.getInstance();
        setFocusable(true);
        setLayout(null);
        setOpaque(false);
        this.addKeyListener(this);
        this.addMouseListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        world.draw(g);
        shop.draw(g, width, height);
        warningToast.draw(g);
        buttonManager.draw(g);
        if (cooldownTicks > 0) {
            cooldownTicks--;
        }
        super.paintComponent(g);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        boolean actionTriggered = buttonManager.mousePressed(e.getX(), e.getY());
        if (actionTriggered) {
            return;
        }
        if (world.getPlayer().getInventory().isModalOpen() || shop.isModalOpen()) {
            return;
        }
        int blockX = e.getX() / 50, blockY = e.getY() / 50;
        Block block = world.getBlocks()[blockX][blockY];
        if (!block.getBlockData().isBreakable()) {
            return;
        }
        if (cooldownTicks > 0) {
            String cooldownStr;
            if (cooldownTicks < 60) {
                cooldownStr = Math.round(cooldownTicks / 60.0 * 1000) + "ms";
            } else {
                cooldownStr = (cooldownTicks / 60) + "s";
            }
            WarningToastManager.getInstance().addToast("Cooldown: " + cooldownStr, 500);
            return;
        }
        Tool tool = world.getPlayer().getCurrentTool();
        ToolData toolData = tool.getToolData();
        int dx = Math.abs(blockX - world.getPlayer().getPosX() / 50);
        int dy = Math.abs(blockY - world.getPlayer().getPosY() / 50);
        if (dy == dx || toolData.getRange() < dx || toolData.getRange() < dy) {
            return;
        }
        block.decreaseHealthWith(tool, world.getPlayer().getInventory());
        cooldownTicks = (int) (toolData.getCooldown() * 60);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Player player = world.getPlayer();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                player.moveUp();
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                player.moveLeft();
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                player.moveDown();
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                player.moveRight();
                break;
            case KeyEvent.VK_E:
                player.getInventory().toggleModal();
                break;
            case KeyEvent.VK_R:
                shop.toggleModal();
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
