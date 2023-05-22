import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {

    private static final int INITIAL_LENGTH = 3;

    private List<Point> body;
    private Direction direction;

    public Snake() {
        body = new ArrayList<>();
        direction = Direction.RIGHT;

        // Initialize the snake's body
        int x = SnakeGamePanel.UNIT_SIZE * INITIAL_LENGTH;
        int y = 0;
        for (int i = 0; i < INITIAL_LENGTH; i++) {
            body.add(new Point(x, y));
            x -= SnakeGamePanel.UNIT_SIZE;
        }
    }

    public void move() {
        Point head = getHead();
        int x = (int) head.getX();
        int y = (int) head.getY();

        switch (direction) {
            case UP:
                y -= SnakeGamePanel.UNIT_SIZE;
                break;
            case DOWN:
                y += SnakeGamePanel.UNIT_SIZE;
                break;
            case LEFT:
                x -= SnakeGamePanel.UNIT_SIZE;
                break;
            case RIGHT:
                x += SnakeGamePanel.UNIT_SIZE;
                break;
        }

        body.add(0, new Point(x, y));
        body.remove(body.size() - 1);
    }

    public void grow() {
        Point tail = body.get(body.size() - 1);
        body.add(new Point((int) tail.getX(), (int) tail.getY()));
    }

    public void reset() {
        body.clear();
        direction = Direction.RIGHT;

        int x = SnakeGamePanel.UNIT_SIZE * INITIAL_LENGTH;
        int y = 0;
        for (int i = 0; i < INITIAL_LENGTH; i++) {
            body.add(new Point(x, y));
            x -= SnakeGamePanel.UNIT_SIZE;
        }
    }

    public boolean collidesWithItself() {
        Point head = getHead();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean collidesWithWall() {
        Point head = getHead();
        int x = (int) head.getX();
        int y = (int) head.getY();
        return x < 0 || y < 0 || x >= SnakeGamePanel.PANEL_WIDTH || y >= SnakeGamePanel.PANEL_HEIGHT;
    }

    public List<Point> getBody(){
        return body;
    }

    public void draw(Graphics g) {
        for (Point bodyPart : body) {
            g.setColor(SnakeGamePanel.DARK_GREEN);
            g.fillRect((int) bodyPart.getX(), (int) bodyPart.getY(), SnakeGamePanel.UNIT_SIZE, SnakeGamePanel.UNIT_SIZE);
        }
    }

    public Point getHead() {
        return body.get(0);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}