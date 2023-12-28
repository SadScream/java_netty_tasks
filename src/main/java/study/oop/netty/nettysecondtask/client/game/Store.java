package study.oop.netty.nettysecondtask.client.game;

import study.oop.netty.nettysecondtask.client.game.models.GameUnit;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Store {
    private static Store instance = null;
    private Store() {}

    private final List<GameUnit> units = new ArrayList<>();
    private GameUnit currentPlayer;

    public static Store getInstance() {
        if (instance == null) {
            instance = new Store();
        }
        return instance;
    }

    public void addUnit(GameUnit unit) {
        this.units.add(unit);
    }

    public void removeUnit(int id) {
        GameUnit unitToRemove = getUnitById(id);
        this.units.remove(unitToRemove);
    }

    public List<GameUnit> getUnits() {
        return units;
    }

    public GameUnit getUnitById(int id) {
        return this.units.stream().filter(unit -> unit.getId() == id).toList().get(0);
    }

    public void updateUnitPosition(int id, Point nextPosition) {
        getUnitById(id).addPathPoint(nextPosition);
    }

    public GameUnit getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(GameUnit currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
