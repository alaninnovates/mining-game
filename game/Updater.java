package game;

public class Updater implements Runnable {
    private final Game game;

    public Updater(Game game) {
        this.game = game;
        Thread repainter = new Thread(this, "RepainterThread");
        repainter.start();
    }

    @Override
    public void run() {
        while (true) {
            game.repaint();
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}