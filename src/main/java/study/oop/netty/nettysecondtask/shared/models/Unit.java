package study.oop.netty.nettysecondtask.shared.models;

import java.awt.*;

public class Unit {
    protected int id;
    protected Point position;

    public Unit() {

    }

    public Unit(int id, int x, int y) {
        this.id = id;
        this.position = new Point(x, y);
    }

    public Unit(int id, Point position) {
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{ id:" + id + ";" + " x:" + position.x + "; y:" + position.y + " }";
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setPosition(int x, int y) {
        this.position = new Point(x, y);
    }
}
