package study.oop.netty.nettyfirsttask.shared.models;

import javafx.util.Pair;

public class Unit {
    private int id;
    private int x;
    private int y;

    public Unit() {

    }

    public Unit(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pair<Integer, Integer> getCoords() {
        return new Pair<Integer, Integer>(x, y);
    }

    public boolean compareCoords(Pair<Integer, Integer> coords) {
        return coords.getKey() == x && coords.getValue() == y;
    }

    public void setCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setCoords(Pair<Integer, Integer> newCoords) {
        this.x = newCoords.getKey();
        this.y = newCoords.getValue();
    }

    @Override
    public String toString() {
        return "{ id:" + id + ";" + " x:" + x + "; y:" + y + " }";
    }
}
