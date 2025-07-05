import javax.swing.JFrame;
public class SnakeGame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        GamePannel gamePannel = new GamePannel();
        frame.add(gamePannel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}