import javax.swing.JFrame;

import game.Game;

public class Runner {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mining Game");

        Game game = new Game();
        frame.add(game);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
