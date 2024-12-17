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
    private final World world;
    private final Shop shop;
    private final Sidebar sidebar;
    private final WarningToastManager warningToast;
    private final ButtonManager buttonManager;
    private final int width = 800;
    private final int height = 800;
    private int cooldownTicks = 0;

    private boolean mouseDown = false;
    private MouseEvent lastMouseEvent;

    public Game() {
        super();
        new Updater(this);
        world = new World(width, height);
        shop = new Shop(world.getPlayer());
        sidebar = new Sidebar(width, height, world.getPlayer(), shop, world.getPlayer().getInventory());
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
        super.paintComponent(g);
        world.draw(g);
        shop.draw(g, width, height);
        sidebar.draw(g);
        warningToast.draw(g);
        buttonManager.draw(g);
        if (cooldownTicks > 0) {
            cooldownTicks--;
        }
        if (lastMouseEvent != null && mouseDown) {
            mouseDownRepeat(lastMouseEvent);
        }
    }

    // extra method that is repeatedly called when the mouse is down.
    // java swing does not call mousePressed repeatedly when the mouse is held down,
    // so we need to call this method in the game loop.
    private void mouseDownRepeat(MouseEvent e) {
        int playerX = world.getPlayer().getPosX(), playerY = world.getPlayer().getPosY();
        int mouseX = e.getX(), mouseY = e.getY();
        int blockX = ((mouseX + playerX - 400) / 50) * 50;
        if (mouseX + playerX - 400 < 0) blockX -= 50;
        int blockY = ((mouseY + playerY - 400) / 50) * 50;
        if (mouseY + playerY - 400 < 0) blockY -= 50;
        Block block = world.getBlock(blockX, blockY);
        if (!block.getBlockData().isBreakable()) {
            return;
        }
        if (cooldownTicks > 0) {
            return;
        }
        Tool tool = world.getPlayer().getCurrentTool();
        ToolData toolData = tool.getToolData();
        int dx = Math.abs(blockX - world.getPlayer().getPosX());
        int dy = Math.abs(blockY - world.getPlayer().getPosY());
        // make sure block is mined only vertically or horizontally
        if (dx > 0 && dy > 0) {
            return;
        }
        // make sure block is not out of range
        if (toolData.getRange() < dx / 50 || toolData.getRange() < dy / 50) {
            return;
        }
        // make sure there is no block between player and the target block
        if (dx > 0) {
            for (int i = 1; i < dx / 50; i++) {
                if (world.getBlock(blockX - i * Integer.signum(blockX - world.getPlayer().getPosX()), blockY)
                        .getBlockData().isBreakable()) {
                    return;
                }
            }
        } else {
            for (int i = 1; i < dy / 50; i++) {
                if (world.getBlock(blockX, blockY - i * Integer.signum(blockY - world.getPlayer().getPosY()))
                        .getBlockData().isBreakable()) {
                    return;
                }
            }
        }
        block.decreaseHealthWith(tool, world.getPlayer().getInventory());
        cooldownTicks = (int) (toolData.getCooldown() * 60);
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
        mouseDown = true;
        lastMouseEvent = e;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
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
