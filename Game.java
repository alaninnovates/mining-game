import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game extends JPanel implements KeyListener, MouseListener {
    private World world;
    private WarningToast warningToast;
    private int width = 800;
    private int height = 800;

    public Game() {
        super();
        new Updater(this);
        world = new World(width, height);
        warningToast = WarningToast.getInstance();
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
        warningToast.draw(g);
        super.paintComponent(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int blockX = e.getX() / 50, blockY = e.getY() / 50;
        Block block = world.getBlocks()[blockX][blockY];
        if (!block.getType().dropsItem()) {
            return;
        }
        Tool tool = world.getPlayer().getCurrentTool();
        int dx = Math.abs(blockX - world.getPlayer().getPosX() / 50);
        int dy = Math.abs(blockY - world.getPlayer().getPosY() / 50);
        if (dy == dx || tool.getRange() < dx || tool.getRange() < dy) {
            return;
        }
        block.decreaseHealthWith(tool, world.getPlayer().getInventory());
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
                player.getInventory().toggleInventoryOpen();
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
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
