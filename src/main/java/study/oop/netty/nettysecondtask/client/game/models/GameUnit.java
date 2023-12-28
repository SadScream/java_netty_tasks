package study.oop.netty.nettysecondtask.client.game.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import study.oop.netty.nettysecondtask.shared.models.Unit;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class GameUnit extends Unit {
    private int speed = 1;
    private Deque<Point> futurePathDeque;
    private Color color;
    private Dimension size = new Dimension(20, 20);

    public GameUnit(int id, Point position) {
        super(id, position);
        futurePathDeque = new ArrayDeque<>();
    }

    public void addPathPoint(Point point) {
        futurePathDeque.addLast(point);
    }

    public Point getNextPathPoint() {
        return futurePathDeque.getLast();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public void render(GraphicsContext context) {
        moveToNextPosition();
        context.setFill(color);
        context.fillRect(position.x, position.y, size.width, size.height);
    }

    private void moveToNextPosition() {
        if (futurePathDeque.isEmpty()) {
            return;
        }

        Point nextPathPoint = futurePathDeque.getLast();
        System.out.println("POSITION " + nextPathPoint + " " + position);
        if (position.equals(nextPathPoint)) {
            futurePathDeque.pop();
            return;
        }

        int deltaX = (position.x == nextPathPoint.x) ? 0 :
                Integer.compare(nextPathPoint.x - position.x, 0) * speed;
        int deltaY = (position.y == nextPathPoint.y) ? 0 :
                Integer.compare(nextPathPoint.y - position.y, 0) * speed;

        setPosition(position.x + deltaX, position.y + deltaY);
    }

    public void setRandomColor() {
        Random rand = new Random();

        int r = rand.nextInt(0, 255);
        int g = rand.nextInt(0, 255);
        int b = rand.nextInt(0, 255);

        this.color = Color.rgb(r, g, b);
    }
}
