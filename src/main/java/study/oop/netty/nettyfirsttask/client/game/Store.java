package study.oop.netty.nettyfirsttask.client.game;

import io.reactivex.rxjava3.subjects.ReplaySubject;
import study.oop.netty.nettyfirsttask.client.game.models.GameUnit;
import study.oop.netty.nettyfirsttask.shared.models.Unit;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class Store {
    private static Store instance = null;
    private Store() {}

    private List<GameUnit> units;

    public static Store getInstance() {
        if (instance == null) {
            instance = new Store();
        }
        return instance;
    }

    public List<GameUnit> getUnits() {
        return units;
    }

    public GameUnit getUnitById(int id) {
        return this.units.stream().filter(unit -> unit.getId() == id).toList().get(0);
    }

    public void setUnits(List<GameUnit> units) {
        this.units = units;
    }

    public void patchUnitPosition(int unitId, Point position) {
        getUnitById(unitId).setFuturePosition(position);
    }
}
