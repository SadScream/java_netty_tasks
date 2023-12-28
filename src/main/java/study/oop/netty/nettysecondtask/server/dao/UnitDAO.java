package study.oop.netty.nettysecondtask.server.dao;

import study.oop.netty.nettysecondtask.shared.models.Unit;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class UnitDAO {
    private static int UNIT_COUNT;
    private final List<Unit> units;

    {
        units = new ArrayList<>();
    }

    public Unit addUnit() {
        Point position = getNewRandomCoordsForUnit();
        Unit newUnit = new Unit(++UNIT_COUNT, position);
        units.add(newUnit);

        return newUnit;
    }

    public void removeUnit(int id) {
        Unit unitToRemove = show(id);

        if (unitToRemove != null) {
            this.units.remove(unitToRemove);
        }
    }

    public List<Unit> index() {
        return units;
    }

    public Unit show(int id) {
        return units.stream().filter(unit -> unit.getId() == id).findAny().orElse(null);
    }

    public void update(int id, Unit updatedPerson) {
        Unit unitToBeUpdated = show(id);

        unitToBeUpdated.setPosition(updatedPerson.getPosition());
    }

    private Point getNewRandomCoordsForUnit() {
        int x = ThreadLocalRandom.current().nextInt(0, 400);
        int y = ThreadLocalRandom.current().nextInt(0, 350);

        return new Point(x, y);
    }
}
