package study.oop.netty.nettyfirsttask.client.game.models;

import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import study.oop.netty.nettyfirsttask.shared.models.Unit;

import java.awt.*;
import java.util.Random;

public class GameUnit extends Unit {
    private int speed = 1;
    private Point futurePosition;
    private Color color;
    private Dimension size = new Dimension(20, 20);

    public PublishSubject<Integer> PositionReach = PublishSubject.create();

    public GameUnit() {
        super();
    }

    public GameUnit(int id, Point position) {
        super(id, position);
    }

    public Point getFuturePosition() {
        return futurePosition;
    }

    public void setFuturePosition(Point futurePosition) {
        this.futurePosition = futurePosition;
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
        updateSelfPosition();
        context.setFill(color);
        context.fillRect(position.x, position.y, size.width, size.height);
    }

    private void updateSelfPosition() {
        if (futurePosition == null) {
            return;
        }

        if (futurePosition.equals(position)) {
            PositionReach.onNext(id);
        } else {

            int deltaX = (position.x == futurePosition.x) ? 0 :
                    Integer.compare(futurePosition.x - position.x, 0) * speed;
            int deltaY = (position.y == futurePosition.y) ? 0 :
                    Integer.compare(futurePosition.y - position.y, 0) * speed;

            setPosition(position.x + deltaX, position.y + deltaY);
        }
    }

    public void setRandomColor() {
        Random rand = new Random();

        int r = rand.nextInt(0, 255);
        int g = rand.nextInt(0, 255);
        int b = rand.nextInt(0, 255);

        this.color = Color.rgb(r, g, b);
    }
}
