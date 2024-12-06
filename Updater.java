public class Updater implements Runnable {
    Thread repainter;
    Game game;

    public Updater(Game game) {
        this.game = game;
        repainter = new Thread(this, "RepainterThread");
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