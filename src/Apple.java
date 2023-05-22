import java.awt.*;
import java.util.Random;

public class Apple {

    private Point position;
    private Snake snake;

    public Apple(Snake snake) {
        this.snake = snake;
        generate();
    }

    public Point getPosition(){
        return position;
    }
    public void generate() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(SnakeGamePanel.PANEL_WIDTH / SnakeGamePanel.UNIT_SIZE) * SnakeGamePanel.UNIT_SIZE;
            y = random.nextInt(SnakeGamePanel.PANEL_HEIGHT / SnakeGamePanel.UNIT_SIZE) * SnakeGamePanel.UNIT_SIZE;
        } while (isCollidingWithSnake(x, y));
        position = new Point(x, y);
    }

    private boolean isCollidingWithSnake(int x, int y) {
        for (Point bodyPart : snake.getBody()) {
            if (bodyPart.getX() == x && bodyPart.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics g) {
        g.setColor(SnakeGamePanel.CRIMSON_RED);
        g.fillRect((int) position.getX(), (int) position.getY(), SnakeGamePanel.UNIT_SIZE, SnakeGamePanel.UNIT_SIZE);
    }
}