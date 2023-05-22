import javax.swing.*;

public class SnakeGame {

    public static final int X_SIZE = 20;
    public static final int Y_SIZE = 20;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snake Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Snake snake = new Snake();
            SnakeGamePanel gamePanel = new SnakeGamePanel(snake);
            frame.getContentPane().add(gamePanel); // Add the panel to the content pane
            frame.pack(); // Adjust frame size based on panel's preferred size
            frame.setLocationRelativeTo(null); // Center the frame on the screen

            frame.add(gamePanel);

            frame.setVisible(true);

            Apple apple = new Apple(snake);
            gamePanel.setApple(apple);
            gamePanel.startGame();
        });
    }
}