import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SnakeGamePanel extends JPanel implements KeyListener {
    public static final Color LIGHT_GREEN = new Color(0xc7f0d8);
    public static final Color DARK_GREEN = new Color(0x43523d);
    public static final Color CRIMSON_RED = new Color(0x7F2929);

    public static final int UNIT_SIZE = 20;
    public static final int PANEL_WIDTH = SnakeGame.X_SIZE * UNIT_SIZE;
    public static final int PANEL_HEIGHT = SnakeGame.Y_SIZE * UNIT_SIZE;
    private static final int MAX_SCORE_TO_WIN = SnakeGame.X_SIZE * SnakeGame.Y_SIZE; // Set the maximum score to win

    private Snake snake;
    private Apple apple;
    private Timer gameTimer;
    private int score;
    private int highScore;

    public SnakeGamePanel(Snake snake) {
        this.snake = snake;
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(LIGHT_GREEN);
        setFocusable(true);
        addKeyListener(this);
        score = 0;
        highScore = 0;
    }

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    public void startGame() {
        snake = new Snake();
        apple = new Apple(snake);
        gameTimer = new Timer(150, e -> {
            snake.move();
            checkCollision();
            repaint();
        });
        gameTimer.start();
    }

    private void checkCollision() {
        Point head = snake.getHead();
        if (head.equals(apple.getPosition())) {
            snake.grow();
            apple.generate();
            score+=10;
        } else if (snake.collidesWithItself() || snake.collidesWithWall()) {
            gameOver();
        }

        checkWinCondition(); // Check win condition after checking collisions
    }

    private void checkWinCondition() {
        if (snake.getBody().size() == MAX_SCORE_TO_WIN) {
            gameTimer.stop();
            JOptionPane.showMessageDialog(this, "Congratulations! You won the game!");
            resetGame();
        }
    }

    private void resetGame() {
        if (score > highScore){
            highScore = score;
        }
        score = 0;
        snake.reset();
        apple.generate();
        gameTimer.start();
    }

    private void gameOver() {
        gameTimer.stop();
        JOptionPane.showMessageDialog(this, "Game Over", "Snake Game", JOptionPane.INFORMATION_MESSAGE);
        resetGame();
    }

    private void drawScore(Graphics g) {
        g.setColor(DARK_GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("Score: " + score, 10, 20); // Display current score
        g.drawString("High Score: " + highScore, 10, 40); // Display high score
    }

    private void drawGrid(Graphics g) {
        g.setColor(LIGHT_GREEN);
        for (int x = 0; x <= PANEL_WIDTH; x += UNIT_SIZE) {
            g.drawLine(x, 0, x, PANEL_HEIGHT);
        }
        for (int y = 0; y <= PANEL_HEIGHT; y += UNIT_SIZE) {
            g.drawLine(0, y, PANEL_WIDTH, y);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        snake.draw(g);
        apple.draw(g);
        drawGrid(g);
        drawScore(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT && snake.getDirection() != Snake.Direction.RIGHT) {
            snake.setDirection(Snake.Direction.LEFT);
        } else if (key == KeyEvent.VK_RIGHT && snake.getDirection() != Snake.Direction.LEFT) {
            snake.setDirection(Snake.Direction.RIGHT);
        } else if (key == KeyEvent.VK_UP && snake.getDirection() != Snake.Direction.DOWN) {
            snake.setDirection(Snake.Direction.UP);
        } else if (key == KeyEvent.VK_DOWN && snake.getDirection() != Snake.Direction.UP) {
            snake.setDirection(Snake.Direction.DOWN);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
